package com.david.equisign;

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
    private String path;

    /**
     * relative name of file
     */
    private String name;
    private File file;



    public FileInfo(String path, String name) {
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

    public void setId(String id) {
        this.id = id;
    }
}


