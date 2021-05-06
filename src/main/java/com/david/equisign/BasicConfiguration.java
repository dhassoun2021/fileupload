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

    public String getUploadsDir() {
        return uploadsDir;
    }

    @JsonCreator
    public BasicConfiguration(@JsonProperty("defaultSize") int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getDefaultSize() {
        return defaultSize;
    }
}
