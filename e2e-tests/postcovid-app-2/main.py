import logging
import os
import pandas as pd
from sqlalchemy import create_engine

from external_analysis import INPUT_TABLES, run

logger = logging.getLogger(__name__)
FORMAT = "[%(filename)s:%(lineno)s - %(funcName)20s() ] %(message)s"
logging.basicConfig(format=FORMAT)
logger.setLevel(logging.INFO)


def main():
    connection_trans_db = {}

    connection_string = f"postgresql://{user_name}:{pwd}@{db_host}/{db_name}"
    engine = create_engine(connection_string)
    connection_trans_db["psql"] = engine

    with engine.connect() as connection:
        input_tables = {
            table_name: pd.read_sql_table(table_name, connection, schema="input") for table_name in INPUT_TABLES
        }
        output_tables = run(input_tables)
        for table_name, data in output_tables.items():
            data.to_sql(table_name, connection, if_exists="append", index=False, schema="output")


db_host = os.environ["PG_HOSTNAME"]
db_name = os.environ["PG_DATABASENAME"]
user_name = os.environ["PG_USERNAME"]
pwd = os.environ["PG_PASSWORD"]

if __name__ == "__main__":
    main()
