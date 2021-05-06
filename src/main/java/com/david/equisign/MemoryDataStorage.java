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

    public MemoryDataStorage (FileEncryptionService fileEncryptionService) {
        this.fileEncryptionService = fileEncryptionService;
    }

    public FileInfo saveFile (InputStream inputStream, String directoryName, String fileName) throws IOException {
        try {
            String pathFile = directoryName + "/" + fileName;
            File destFile = new File(pathFile);
            fileEncryptionService.encrypt(inputStream, destFile);
            // writeToFile(inputStream,pathFile);
            String id = UUID.randomUUID().toString();
            FileInfo fileInfo = new FileInfo(id, pathFile, fileName);
            datas.put(id, fileInfo);
            return fileInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FileInfo readFile (String id) throws IOException {
        FileInfo fileInfo = datas.get(id);
        if (fileInfo == null) {
            throw new FileNotFoundException ("File does not exists for id " + id);
        }
        try {
            //OutputStream outputStream = new FileOutputStream(fileInfo.getName());
            File fileEncrypted = new File(fileInfo.getPath());
            File fileDecrypted = File.createTempFile("decrypted", "tmp");
            fileEncryptionService.decrypt(fileEncrypted, fileDecrypted);
            //Files.copy(Path.of(fileInfo.getPath()),outputStream);
            //fileInfo.setOutputStream(outputStream);
            fileInfo.setFile(fileDecrypted);
            return fileInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
        int read;
        final int BUFFER_LENGTH = 1024;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        OutputStream out = new FileOutputStream(uploadedFileLocation);
        while ((read = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
    }

}
