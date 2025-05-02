#!/bin/bash

# The client credentials have to be passed as command line arguments:
client_id=$1
key_path=$2
key_pwd=$3

# get authorization from EuroDaT
token=$(./create_jwt.sh \
    --client_id $client_id \
    --url https://app.int.eurodat.org \
    --key_path $key_path \
    --key_pwd $key_pwd)

echo "$token"

# do the clean up
echo "cleaning up ..."

# define variables on which you want to call clean up requests

app_name="test1"

#

if [ -n "$transaction_id" ]; then
echo transaction
curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/transactions/$transaction_id" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"
fi

if [ -n "$workflow_name" ]; then
echo workflow
curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name/workflows/$workflow_name" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"
fi

if [ -n "$image_id" ]; then
echo image
curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name/images/$image_id" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"
fi

if [ -n "$app_name" ]; then
echo app_name
curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"
fi
