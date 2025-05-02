from fastapi import FastAPI
from psycopg2.extras import RealDictCursor
import os
import psycopg2
import uvicorn
import json

app = FastAPI()
connection_trans_db = {}
cursor_trans_db = {}


@app.on_event("startup")
async def startup_event():
    print("Using user password for authentication to login to transaction db.")
    connection_trans_db["psql"] = psycopg2.connect(
        host=db_host, dbname=db_name, user=user_name, password=pwd
    )
    connection_trans_db["psql"].autocommit = True
    cursor_trans_db["psql"] = connection_trans_db["psql"].cursor(
        cursor_factory=RealDictCursor
    )

    query = """INSERT INTO output.data
        (id,research_data,security_column)
        VALUES
        ('001','test research data','postcovidclient');"""

    cursor_trans_db["psql"].execute(query)


@app.on_event("shutdown")
def shutdown_event():
    cursor_trans_db["psql"].close()
    connection_trans_db["psql"].close()


@app.get("/actuator/health/readiness")
def health():
    return "Healthy"


db_host = os.environ["PG_HOSTNAME"]
db_name = os.environ["PG_DATABASENAME"]
user_name = os.environ["PG_USERNAME"]
pwd = os.environ["PG_PASSWORD"]


if __name__ == "__main__":
    uvicorn.run("main:app", log_level="info", host="0.0.0.0", port=8080)
