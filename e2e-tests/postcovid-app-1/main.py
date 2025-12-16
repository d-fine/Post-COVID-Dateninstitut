import logging
import os

import psycopg2
from psycopg2.extras import RealDictCursor

logger = logging.getLogger(__name__)
FORMAT = "[%(filename)s:%(lineno)s - %(funcName)20s() ] %(message)s"
logging.basicConfig(format=FORMAT)
logger.setLevel(logging.INFO)


def main():
    connection_trans_db = {}
    cursor_trans_db = {}

    try:
        logger.info(
            "Using user password for authentication to login to transaction db."
        )
        connection_trans_db["psql"], cursor_trans_db["psql"] = open_connection(db_name) 

        logger.info("Writing json data to output table")
        query = """INSERT INTO output.data
            (id,research_data,security_column)
            VALUES
            ('001','test research data','postcovidclient');"""

        cursor_trans_db["psql"].execute(query)

    except psycopg2.Error as exc:
        logger.error(f"Database error: {exc}")
        raise
    except Exception as exc:
        logger.error(f"Unexpected error: {exc}")
        raise
    finally:
        if cursor_trans_db.get("psql"):
            cursor_trans_db["psql"].close()
        if connection_trans_db.get("psql"):
            connection_trans_db["psql"].close()


def open_connection(db: str):
    connection = psycopg2.connect(host=db_host, dbname=db, user=user_name, password=pwd)
    connection.autocommit = True
    cursor = connection.cursor(cursor_factory=RealDictCursor)
    return connection, cursor


db_host = os.environ["PG_HOSTNAME"]
db_name = os.environ["PG_DATABASENAME"]
user_name = os.environ["PG_USERNAME"]
pwd = os.environ["PG_PASSWORD"]

if __name__ == "__main__":
    main()
