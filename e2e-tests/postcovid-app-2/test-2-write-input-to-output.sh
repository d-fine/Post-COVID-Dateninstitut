#!/bin/bash

LOG_FILE="log.log"

# read the credentials from file
credentials_file="credentials.txt"
client_id="$(grep client_id_1 $credentials_file | awk -F '=' '{print $2}')"
key_path="$(grep key_path_1 $credentials_file | awk -F '=' '{print $2}')"
client_id_2="$(grep client_id_2 $credentials_file | awk -F '=' '{print $2}')"
key_path_2="$(grep key_path_2 $credentials_file | awk -F '=' '{print $2}')"
key_pwd="$(grep key_pwd $credentials_file | awk -F '=' '{print $2}')"
image_location="$(grep image_location $credentials_file | awk -F '=' '{print $2}')"
registry_user_name="$(grep registry_user_name $credentials_file | awk -F '=' '{print $2}')"
registry_pwd="$(grep registry_pwd $credentials_file | awk -F '=' '{print $2}')"

# get authorization from EuroDaT
token=$(../create_jwt.sh \
    --client_id $client_id \
    --url https://app.int.eurodat.org \
    --key_path $key_path \
    --key_pwd $key_pwd)

token_2=$(../create_jwt.sh \
    --client_id $client_id_2 \
    --url https://app.int.eurodat.org \
    --key_path $key_path_2 \
    --key_pwd $key_pwd)

echo "$token" | tee -a $LOG_FILE
echo "$token_2" | tee -a $LOG_FILE

# set up the test parameters
suffix=$(openssl rand -base64 8 | tr -d /=+ | cut -c -8 | awk '{print tolower($0)}')
app_name="postcovidtestapp"$suffix
image_name="postcovidtestimg"$suffix
output_table="data"
workflow_name="postcovidtestwf"$suffix
target='["{\"id\":1,\"research_data\":\"test research data\",\"security_column\":\"'${client_id_2}'\"}"]'

# print test parameters:
echo "app_name: $app_name" | tee -a $LOG_FILE
echo "image_name: $image_name" | tee -a $LOG_FILE
echo "workflow_name: $workflow_name" | tee -a $LOG_FILE

# register application
transaction_ddl_string=$(cat ddl.sql  | tr '\n' ' ')
ddl_string="\"transactionDDL\": \"$transaction_ddl_string\", \"safeDepositDDL\": \"string\" "
id_string="\"id\": \"$app_name\""
security_mapping_string='"tableSecurityMapping": [{ "rowBaseOutputSecurityColumn": "security_column", "tableName": "data"}]'
request_body="{ $ddl_string, $id_string, $security_mapping_string }"
curl -X 'POST' \
  'https://app.int.eurodat.org/api/v1/app-service/apps' \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token" \
  -H 'Content-Type: application/json' \
  -d "$request_body"

# register image
output=$(curl -X 'POST' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name/images" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token" \
  -H 'Content-Type: application/json' \
  -d "{ \"registryUserName\": \"$registry_user_name\",
  \"registryPassword\": \"$registry_pwd\",
  \"location\": \"$image_location\" }")
image_id=$(echo "$output" | jq -r '.id')
echo "image_id: $image_id" | tee -a $LOG_FILE

# register workflow
sleep 20
curl -X 'POST' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name/workflows" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token" \
  -H 'Content-Type: application/json' \
  -d "{
  \"imageId\": \"$image_id\",
  \"startCommand\": [\"python\", \"main.py\"],
  \"workflowId\": \"$workflow_name\"
}"

# start transaction
output=$(curl -X 'POST' \
  'https://app.int.eurodat.org/api/v1/transactions' \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token" \
  -H 'Content-Type: application/json' \
  -d "{ \"provider\": [], \"appId\": \"$app_name\", \"consumer\": [] }")

transaction_id=$(echo "$output" | jq -r '.id')
echo "transaction_id: $transaction_id" | tee -a $LOG_FILE
echo $output | tee -a $LOG_FILE

# post input data to input.data table
table="\"table\":\"$output_table\""
transactionId="\"transactionId\":\"$transaction_id\""
data="\"data\":$target"
request_body="{ $table, $transactionId, $data }"
echo $request_body | tee -a $LOG_FILE

output=$(curl -X 'POST' \
  "https://app.int.eurodat.org/api/v1/participants/$client_id/data" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token" \
  -H 'Content-Type: application/json' \
  -d "$request_body")
echo
echo $output | tee -a $LOG_FILE
echo

# start workflow
output=$(curl -X 'POST' \
  "https://app.int.eurodat.org/api/v1/transactions/$transaction_id/workflows" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token" \
  -H 'Content-Type: application/json' \
  -d "{ \"workflowDefinitionId\": \"$workflow_name\" }")
workflow_id=$(echo "$output" | jq -r '.workflowRunId')
echo "workflow_id: $workflow_id" | tee -a $LOG_FILE
echo $output >> $LOG_FILE | tee -a $LOG_FILE

# get the actual data for both clients
sleep 20

echo
echo "client_id: $client_id" | tee -a $LOG_FILE
output=$(curl -X 'GET' \
  "https://app.int.eurodat.org/api/v1/participants/$client_id/data?table=$output_table&transactionId=$transaction_id" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token")

echo 'Expecting empty due to row level security filter' | tee -a $LOG_FILE
echo "$output" | tee -a $LOG_FILE
echo
echo "client_id: $client_id_2" | tee -a $LOG_FILE
output_2=$(curl -X 'GET' \
  "https://app.int.eurodat.org/api/v1/participants/$client_id_2/data?table=$output_table&transactionId=$transaction_id" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token_2")

# compare actual data to target data:
echo
echo "output: $output_2" | tee -a $LOG_FILE
echo
if [ "$output" = "$target" ]; then
  echo "The test is passed successfully" | tee -a $LOG_FILE
fi
if [ "$output" != "$target" ]; then
  echo "Error: The test is not passed" | tee -a $LOG_FILE
fi
echo
echo

# do the clean up
echo "cleaning up ..."
sleep 20

curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/transactions/$transaction_id" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"

curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name/workflows/$workflow_name" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"

curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name/images/$image_id" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"

sleep 20
curl -X 'DELETE' \
  "https://app.int.eurodat.org/api/v1/app-service/apps/$app_name" \
  -H 'accept: application/json' \
  -H "Authorization: Bearer $token"
