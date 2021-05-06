package com.david.equisign;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class BasicConfiguration extends Configuration {
    @NotNull
    private final int defaultSize;

    @JsonProperty
    private String uploadsDir;

    @JsonProperty
    private int maxSizeRequest;

    @JsonProperty
    private String tmpDir;

    public String getUploadsDir() {
        return uploadsDir;
    }

    @JsonCreator
    public BasicConfiguration(@JsonProperty("defaultSize") int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public BasicConfiguration () {
        defaultSize = 0;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public int getMaxSizeRequest() {
        return maxSizeRequest;
    }

    public void setMaxSizeRequest(int maxSizeRequest) {
        this.maxSizeRequest = maxSizeRequest;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public void setUploadsDir(String uploadsDir) {
        this.uploadsDir = uploadsDir;
    }

    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }
}
