package com.david.equisign;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class BasicConfiguration extends Configuration {


    @NotNull
    @JsonProperty
    private String uploadsDir;

    @NotNull
    @JsonProperty
    private int maxSizeRequest;


    public String getUploadsDir() {
        return uploadsDir;
    }

    public int getMaxSizeRequest() {
        return maxSizeRequest;
    }

    public void setMaxSizeRequest(int maxSizeRequest) {
        this.maxSizeRequest = maxSizeRequest;
    }

    public void setUploadsDir(String uploadsDir) {
        this.uploadsDir = uploadsDir;
    }

}
