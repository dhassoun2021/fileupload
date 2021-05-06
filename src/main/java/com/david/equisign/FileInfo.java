package com.david.equisign;

import java.io.OutputStream;

public class FileInfo {

    private String id;
    private String path;
    private String name;
    private OutputStream outputStream;

    public FileInfo(String id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
