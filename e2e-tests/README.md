# Authorization with EuroDaT:
- The script `create_jwt.sh` is taken from the EuroDaT repository. 
Currently, it only works correctly when executed in a Linux terminal; when executed in 
PowerShell, the resulting JWT is not accepted by EuroDaT.
- It has been slightly adapted, to accept the private key password as command line argument, to 
  facilitate test runs

# Test setup
For the e2e tests to run, EuroDaT has to know the following clients and Python apps:

## First client
- private key: ../datenmodell-backend/src/main/resources/keystore.pem

## Second client
- private key: secrets/keystore.pem

## postcovid-app-1

This test uses one EuroDaT client (first client). The python apps has a hardcode value that is written into the output schema and then read by the client

## postcovid-app-2

This test uses two EuroDaT clients (first client, second client), one to push data into the input schema, then the python app writes it to the output schema.
Via row level security, only the second client should be able to read the row

