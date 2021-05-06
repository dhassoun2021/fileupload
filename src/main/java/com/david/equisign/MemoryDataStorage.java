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

    public FileInfo saveFile (InputStream inputStream, String directoryName, String fileName) throws IOException {
        String pathFile = directoryName + "/" + fileName;
        writeToFile(inputStream,pathFile);
        String id = UUID.randomUUID().toString();
        FileInfo fileInfo = new FileInfo(id,pathFile, fileName);
        datas.put(id,fileInfo);
        return  fileInfo;
    }

    public FileInfo readFile (String id) throws IOException {
        FileInfo fileInfo = datas.get(id);
        if (fileInfo == null) {
            throw new FileNotFoundException ("File does not exists for id " + id);
        }
        OutputStream outputStream = new FileOutputStream(fileInfo.getName());
        Files.copy(Path.of(fileInfo.getPath()),outputStream);
        fileInfo.setOutputStream(outputStream);
        return fileInfo;
    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
        int read;
        final int BUFFER_LENGTH = 1024;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
    }

}
