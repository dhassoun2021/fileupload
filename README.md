FileUpload
=

Exercice for equisign to create API for upload and download files (with dropwizard)

##Prequisite

To launch application you need to have jdk 11.

## Configuration
To set parmeters you need to update upolad.yaml file, specified in src/main/resources .
In this file you would update theses parameters:

- uploadsDir: the directory where file uploaded will be stored
- tmpDir: the directory where decrypyted file will be stored
- maxSizeRequest : Maximum size on request content in octet

## How to run it

I assume you have latest version of Maven and Java JDK 11 on your **PATH**

Go to fileupload-main/fileupload-main directory and after that, launch following commands:
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

Response will be:
{"id":"fe01d05d-7990-47c4-a9c7-1c09eb8cec05","name":"a.txt"} (id is the id corresponding to file uploaded)

## Test download file
To test download file, launch following command:

curl http://localhost:9000/api/equisign/files/{idFile}

idFile is the id of file to download

For example to download file with id 123, laucnch :

curl http://localhost:9000/api/equisign/files/123