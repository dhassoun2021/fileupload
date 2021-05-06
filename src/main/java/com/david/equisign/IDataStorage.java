package com.david.equisign;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IDataStorage {

    FileInfo saveFile (InputStream inputStream, String directoryName, String fileName) throws IOException;

    FileInfo readFile (String id) throws IOException;
}
