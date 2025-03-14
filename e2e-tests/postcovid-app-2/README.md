## Authorization with EuroDaT:
- The script `create_jwt.sh` is taken from the EuroDaT repository. 
Currently, it only works correctly when executed in a Linux terminal; when executed in 
PowerShell, the resulting JWT is not accepted by EuroDaT.
- It has been slightly adapted, to accept the private key password as command line argument, to 
  facilitate test runs

## How to push an image to the Azure Container Registry of the project

Prerequisites:
- An Azure account for the general d-fine Azure Subscription and permissions for the project 
  resource group `dfine-BMI001N1-rg`
- Docker Desktop is running

Necessary steps:
- Build and tag the image, e.g. from PowerShell via the command: `docker build -t postcovid.azurecr.<IMAGE_NAME>`
- Build and tag the image, e.g. from PowerShell via the command: `docker build -t postcovid.azurecr.io/samples/<IMAGE_NAME> -f <PATH_TO_DOCKERFILE> .`
- Login to Azure, e.g. from PowerShell via the command: `az login`
- Login to Azure Container Registry (ACR) `postcovid`, e.g. from PowerShell via the command: `az 
  acr login --name postcovid`
- Push the image to the ACR,  e.g. from PowerShell via the command: `docker push postcovid.azurecr.io/samples/<IMAGE_NAME>`



