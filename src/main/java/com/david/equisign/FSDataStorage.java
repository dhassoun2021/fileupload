package com.david.equisign;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Store file uploaded in local file system
 */
public class FSDataStorage implements IDataStorage{

    private static final Logger LOG = Logger.getGlobal();
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
            String idFile = UUID.randomUUID().toString();
            String pathFile = configuration.getUploadsDir() + "/" + idFile;
            File destFile = new File(pathFile);

            //encrypt file uploaded
            fileEncryptionService.encrypt(inputStream, destFile);

            LOG.log(Level.INFO,"File was saved and ciphered at " + pathFile);
            FileInfo fileInfo = new FileInfo(idFile,pathFile,fileName);
            fileInfoDao.save(fileInfo);
            LOG.log(Level.INFO,"File information was stored");
            return fileInfo;
    }



    public FileInfo readFile (String id) throws FileNotFoundException, FileUploadException {
        // Get file information
        Optional<FileInfo> optionalFileInfo = fileInfoDao.read(id);
        if (optionalFileInfo.isEmpty()) {
            throw new FileNotFoundException ("File does not exists for id " + id);
        }
        try {
            FileInfo fileInfo = optionalFileInfo.get();
            File fileEncrypted = new File(fileInfo.getPath());

            //create temp file for decription
            File fileDecrypted = File.createTempFile("decrypted", ".tmp",fileTmpDirectory);

            //decrypt file
            fileEncryptionService.decrypt(fileEncrypted, fileDecrypted);
            LOG.log(Level.INFO,"File with path " + fileInfo.getPath() + " was decrypted sucessfully");
            fileInfo.setFile(fileDecrypted);
            return fileInfo;
        } catch (IOException ex) {
            throw new FileUploadException(ex.getMessage());
        }
    }



}
