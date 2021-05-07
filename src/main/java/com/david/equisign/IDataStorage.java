package com.david.equisign;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface to expose method to save and read file uploaded
 */
public interface IDataStorage {

    /**
     * Save file in a data storage
     * @param inputStream data to save
     * @param fileName name of file uploaded
     * @return
     * @throws FileUploadException
     */
    FileInfo saveFile (InputStream inputStream, String fileName) throws FileUploadException;

    /**
     * Read File from id of file uploaded
     * @param id id of file uploaded
     * @return
     * @throws FileUploadException
     * @throws FileNotFoundException
     */
    FileInfo readFile (String id) throws FileUploadException, DataNotFoundException;
}
