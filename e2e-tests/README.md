## Authorization with EuroDaT:
- The script `create_jwt.sh` is taken from the EuroDaT repository. 
Currently, it only works correctly when executed in a Linux terminal; when executed in 
PowerShell, the resulting JWT is not accepted by EuroDaT.
- It has been slightly adapted, to accept the private key password as command line argument, to 
  facilitate test runs


# Tests

### postcovid-app-1

This test uses one EuroDaT client (postcovidclient). The python apps has a hardcode value that is written into the output schema and then read by the client

### postcovid-app-2

This test uses two EuroDaT clients (postcovidclient, postcovidclient2), one to push data into the input schema, then the python app writes it to the output schema.
Via row level security, only the second client should be able to read the row

