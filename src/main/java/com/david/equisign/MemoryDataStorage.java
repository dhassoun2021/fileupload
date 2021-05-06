package com.david.equisign;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryDataStorage implements IDataStorage{

    private Map<String,FileInfo> datas = new ConcurrentHashMap<>();

    private FileEncryptionService fileEncryptionService;
    private BasicConfiguration configuration;
    private File fileTmpDirectory;

    public MemoryDataStorage (FileEncryptionService fileEncryptionService, BasicConfiguration configuration) {
        this.fileEncryptionService = fileEncryptionService;
        this.configuration = configuration;
        fileTmpDirectory = new File( configuration.getTmpDir());
    }

    public FileInfo saveFile (InputStream inputStream, String fileName) throws FileUploadException {

            String pathFile = configuration.getUploadsDir() + "/" + fileName;
            File destFile = new File(pathFile);
            fileEncryptionService.encrypt(inputStream, destFile);
            // writeToFile(inputStream,pathFile);
            String id = UUID.randomUUID().toString();
            FileInfo fileInfo = new FileInfo(id, pathFile, fileName);
            datas.put(id, fileInfo);
            return fileInfo;

    }

    public FileInfo readFile (String id) throws FileNotFoundException, FileUploadException {
        FileInfo fileInfo = datas.get(id);
        if (fileInfo == null) {
            throw new FileNotFoundException ("File does not exists for id " + id);
        }
        try {
            File fileEncrypted = new File(fileInfo.getPath());
            File fileDecrypted = File.createTempFile("decrypted", ".tmp",fileTmpDirectory);
            fileEncryptionService.decrypt(fileEncrypted, fileDecrypted);
            fileInfo.setFile(fileDecrypted);
            return fileInfo;
        } catch (IOException ex) {
            throw new FileUploadException(ex.getMessage());
        }
    }



}
