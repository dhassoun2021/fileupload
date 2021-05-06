package com.david.equisign;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FSDataStorage implements IDataStorage{

    private Map<String,FileInfo> datas = new ConcurrentHashMap<>();

    private FileEncryptionService fileEncryptionService;
    private BasicConfiguration configuration;
    private File fileTmpDirectory;
    private IFileInfoDao fileInfoDao;

    public FSDataStorage(FileEncryptionService fileEncryptionService, IFileInfoDao fileInfoDao, BasicConfiguration configuration) {
        this.fileEncryptionService = fileEncryptionService;
        this.configuration = configuration;
        this.fileInfoDao = fileInfoDao;
        fileTmpDirectory = new File( configuration.getTmpDir());
    }

    public FileInfo saveFile (InputStream inputStream, String fileName) throws FileUploadException {
            String pathFile = configuration.getUploadsDir() + "/" + fileName;
            File destFile = new File(pathFile);
            fileEncryptionService.encrypt(inputStream, destFile);
            FileInfo fileInfo = new FileInfo(pathFile, fileName);
            fileInfoDao.save(fileInfo);
            return fileInfo;
    }

    public FileInfo readFile (String id) throws FileNotFoundException, FileUploadException {
        Optional<FileInfo> optionalFileInfo = fileInfoDao.read(id);
        if (optionalFileInfo.isEmpty()) {
            throw new FileNotFoundException ("File does not exists for id " + id);
        }
        try {
            FileInfo fileInfo = optionalFileInfo.get();
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
