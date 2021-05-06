package com.david.equisign;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IDataStorage {

    FileInfo saveFile (InputStream inputStream, String fileName) throws FileUploadException;

    FileInfo readFile (String id) throws FileUploadException, FileNotFoundException;
}
