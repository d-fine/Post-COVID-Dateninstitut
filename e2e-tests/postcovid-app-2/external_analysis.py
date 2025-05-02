# An example file that can be uploaded to the platform to contain user analysis based on pandas and abstracting the integration into EuroDaT
# 
# To properly function, this script must expose a list of input table names as strings. Tables from the input schema will be read using those names and
# returned as Pandas dataframes
INPUT_TABLES = ["data"]

# the following function servers as the entrypoint

def run(input_tables: dict) -> dict:
    """
    The entry point for the custom analysis script

    :param dict input_tables: A dictionary of pandas dataframes with input_tables.keys == INPUT_TABLES
    :return dict: the results of the analysis as pandas dataframes. Requires a string as keys, which are used to write
        to the output schema
    """
    for name, table in input_tables.items():
        table["security_column"] = "postcovidclient"
    return input_tables