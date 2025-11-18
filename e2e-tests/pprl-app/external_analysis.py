# An example file that can be uploaded to the platform to contain user analysis based on pandas and abstracting the integration into EuroDaT
# 
# To properly function, this script must expose a list of input table names as strings. Tables from the input schema will be read using those names and
# returned as Pandas dataframes
import pandas as pd

from typing import Dict

INPUT_TABLES = ["study_data", "pii_data"]

pii_data_columns = ["vorname", "nachname", "geburtstag", "geschlecht", "wohnort"]
DATA_CONSUMER = "postcovidclient"


def run(input_tables: Dict[str, pd.DataFrame]) -> dict:
    """
    The entry point for the custom analysis script

    :param dict input_tables: A dictionary of pandas dataframes with input_tables.keys == INPUT_TABLES
    :return dict: the results of the analysis as pandas dataframes. Requires a string as keys, which are used to write
        to the output schema
    """
    my_study = input_tables["study_data"]
    my_pii = input_tables["pii_data"]

    return {}


if __name__ == '__main__':
    test_my_pii = pd.DataFrame(columns=["id"] + pii_data_columns)
    test_other_pii = pd.DataFrame(columns=["id"] + pii_data_columns)
    test_data = pd.DataFrame(columns=["id", "test"])
