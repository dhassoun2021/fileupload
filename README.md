FileUpload
=

Exercice for equisign to create API for upload and download files (with dropwizard)



## How to run it

I assume you have latest version of Maven and Java JDK 11 on your **PATH**

```bash
$ mvn clean package

$ java -jar target\fileupload-1.0-SNAPSHOT-1.0.jar server upload.yaml
```
## Test upload file

To test it launch following command with curl:

curl -X POST http://localhost:9000/api/equisign/files -H "Content-Type: multipart/form-data" -F "fileData=@[FILE]"

FILE is the file to upload.

For example to upload file named file.txt, launch following command:

curl -X POST http://localhost:9000/api/equisign/files -H "Content-Type: multipart/form-data" -F "fileData=@file.txt"

## Test download file
To test download file, launch following command:

curl http://localhost:9000/api/equisign/files/{idFile}

idFile is the id of file to download

For example to download file with id 123, laucnch :

curl http://localhost:9000/api/equisign/files/123