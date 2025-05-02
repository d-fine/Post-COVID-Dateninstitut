from fastapi import FastAPI
from sqlalchemy import create_engine
import pandas as pd
import os
import uvicorn

from external_analysis import INPUT_TABLES, run

app = FastAPI()
connection_trans_db = {}


@app.on_event("startup")
async def startup_event():
    connection_string = f"postgresql://{user_name}:{pwd}@{db_host}/{db_name}"
    engine = create_engine(connection_string)
    connection_trans_db["psql"] = engine

    with engine.connect() as connection:
        input_tables = {
            table_name: pd.read_sql_table(table_name, engine, schema="input") for table_name in INPUT_TABLES
        }
        output_tables = run(input_tables)
        for table_name, data in output_tables.items():
            data.to_sql(table_name, engine, if_exists="append", index=False, schema="output")

@app.on_event("shutdown")
def shutdown_event():
    connection_trans_db["psql"].dispose()


@app.get("/actuator/health/readiness")
def health():
    return "Healthy"

db_host = os.environ["PG_HOSTNAME"]
db_name = os.environ["PG_DATABASENAME"]
user_name = os.environ["PG_USERNAME"]
pwd = os.environ["PG_PASSWORD"]


if __name__ == "__main__":
    uvicorn.run("main:app", log_level="info", host="0.0.0.0", port=8080)
