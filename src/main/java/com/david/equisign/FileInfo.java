package com.david.equisign;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;

/**
 * Information about file uploaded
 */
public class FileInfo {

    /**
     * Id generated when saving file
     */
    private String id;

    /**
     * Abolute path of file
     */
    @JsonIgnore
    private String path;

    /**
     * relative name of file
     */
    private String name;

    @JsonIgnore
    private File file;



    public FileInfo(String id, String path, String name) {
        this.path = path;
        this.name = name;
        this.id = id;
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

    public void setId(String id) {
        this.id = id;
    }
}


