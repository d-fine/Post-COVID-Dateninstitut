#!/bin/bash
set -e

# Retrieves an access token for a registered client in an EuroDaT cluster
# --client_id is the ID of the client to register. Obligatory
# --key_path is the path to the private key .pem file. Defaults to "./keystore.pem"
# --key_pwd is the password of the private key .pem file.
# --url is the URL of the EuroDaT cluster. Defaults to EuroDaT Integration
# --cacert is the truststore of the EuroDaT cluster. Only necessary in case of self-signed EuroDaT certificates

# Parse arguments
while [[ "$#" -gt 0 ]]; do
    case $1 in
        --client_id) client_id="$2"; shift ;;
        --url) url="$2"; shift ;;
        --key_path) key_path="$2"; shift ;;
        --key_pwd) key_pwd="$2"; shift ;;
        --cacert) cacert="$2"; shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done
if [ -z "$client_id" ]; then
    echo "Error: --client_id is required"
    exit 1
fi
if [ -z "$url" ]; then
    url="https://app.int.eurodat.org"
fi
if [ -z "$key_path" ]; then
    key_path=./keystore.pem
fi
if [ -z "$key_pwd" ]; then
    echo "Error: --key_pwd is required"
    exit 1
fi
if [ ! -z "$cacert" ]; then
    cacertargument="--cacert $cacert"
fi

now=$(date -u +%s)
# Define JWT payload
iat=$now
exp=$((now+5))
sub=$client_id
jti=$(openssl rand -hex 16)
iss=$client_id
aud=$url"/auth/realms/eurodat/protocol/openid-connect/token"

# Create the JWT header
header=$(echo -n '{"alg":"RS256","typ":"JWT"}' | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
# Create the JWT payload
payload=$(echo -n "{\"iat\":\"$iat\",\"exp\":\"$exp\",\"sub\":\"$sub\",\"jti\":\"$jti\",\"iss\":\"$iss\",\"aud\":\"$aud\"}" | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
# Sign the token
signature=$(echo -n "$header.$payload" | openssl dgst -sha256 -sign $key_path -passin pass:$key_pwd | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
# Print the JWT token
signed_jwt_token="$header.$payload.$signature"

access_token=$(curl -X POST -s $url"/auth/realms/eurodat/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials" \
  -d "scope=openid" \
  -d "client_id=$client_id" \
  -d "client_assertion=$signed_jwt_token" \
  -d "client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer" \
  $cacertargument | jq -r '.access_token' \
)
echo "$access_token"
