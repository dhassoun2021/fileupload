package com.david.equisign;

import java.io.*;

import java.util.Optional;
import java.util.UUID;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Store file uploaded in local file system
 */
public class FSDataStorage implements IDataStorage{

    private static final Logger LOG = Logger.getGlobal();

    private FileEncryptionService fileEncryptionService;
    private BasicConfiguration configuration;

    /**
     * Directory where temporary decrypted files will be stored
     */
    private IFileInfoDao fileInfoDao;

    public FSDataStorage(FileEncryptionService fileEncryptionService, IFileInfoDao fileInfoDao, BasicConfiguration configuration) {
        this.fileEncryptionService = fileEncryptionService;
        this.configuration = configuration;
        this.fileInfoDao = fileInfoDao;
    }

    public FileInfo saveFile (InputStream inputStream, String fileName) throws FileUploadException {
        FileOutputStream outputStream = null;
        InputStream streamData= null;

        streamData = inputStream;
        try {
                String idFile = UUID.randomUUID().toString();
                String pathFile = configuration.getUploadsDir() + "/" + idFile;
                outputStream = new FileOutputStream(pathFile);

                //encrypt file uploaded
                streamData = encryptData(inputStream);

                //Copy file on file system
                CopyUtil.copy(streamData, outputStream);
                LOG.log(Level.INFO, "File was saved and ciphered at " + pathFile);

                //Store fine information
                FileInfo fileInfo = new FileInfo(idFile, pathFile, fileName);
                fileInfoDao.save(fileInfo);
                LOG.log(Level.INFO, "File information was stored");
                return fileInfo;
            } catch (IOException ex) {
                throw new FileUploadException(ex.getMessage());
            } finally {
            try {
                if (streamData != null) {
                    streamData.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                throw new FileUploadException(ex.getMessage());
            }
        }
    }

    private InputStream encryptData (InputStream inputStream) throws FileUploadException {
        return fileEncryptionService.cipher(inputStream);
    }

    private InputStream decipherData (InputStream inputStream) throws FileUploadException {
        return fileEncryptionService.decipher(inputStream);
    }



    public FileInfo readFile (String id) throws DataNotFoundException, FileUploadException {
        // Get file information
        Optional<FileInfo> optionalFileInfo = fileInfoDao.read(id);
        InputStream streamingFile = null;
        if (optionalFileInfo.isEmpty()) {
            throw new DataNotFoundException ("File does not exists for id " + id);
        }
        try {
            FileInfo fileInfo = optionalFileInfo.get();
            streamingFile = new FileInputStream(fileInfo.getPath());

            //decrypt file
            streamingFile = decipherData(streamingFile);
            LOG.log(Level.INFO,"File with path " + fileInfo.getPath() + " was decrypted sucessfully");
            fileInfo.setStreamData(streamingFile);
            return fileInfo;
        } catch (IOException ex) {
            throw new FileUploadException(ex.getMessage());

        }
    }



}
