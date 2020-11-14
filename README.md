# Integracao-S3-com-Spring-Boot
#### Environments
Create user at `Identity and Access Management` (IAM Console) with roles to S3
* SECRET_KEY
* CLIENT_KEY
#### File endpoint
* POST
```sh
curl --request POST \
  --url http://localhost:8080/ \
  --header 'Content-Type: multipart/form-data' \
  --form file='FILE_LOCATION'
```
