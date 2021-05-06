package com.david.equisign;

import java.io.File;


public class FileInfo {

    private String id;
    private String path;
    private String name;
    private File file;


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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}


