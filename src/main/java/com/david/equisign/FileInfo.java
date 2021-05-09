package com.david.equisign;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

/**
 * Information about file uploaded
 */
public class FileInfo {

    /**
     * Id generated when saving file
     */
    private final String id;

    /**
     * Abolute path of file
     */
    @JsonIgnore
    private final String path;

    /**
     * relative name of file
     */
    private final String name;

    @JsonIgnore
    private File file;

    private InputStream streamData;



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInfo fileInfo = (FileInfo) o;
        return Objects.equals(id, fileInfo.id) && Objects.equals(path, fileInfo.path) && Objects.equals(name, fileInfo.name);
    }

    public InputStream getStreamData() {
        return streamData;
    }

    public void setStreamData(InputStream streamData) {
        this.streamData = streamData;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, name);
    }


}


