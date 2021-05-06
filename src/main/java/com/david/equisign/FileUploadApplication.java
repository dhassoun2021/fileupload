package com.david.equisign;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class FileUploadApplication extends Application<BasicConfiguration> {

    public static void main(String[] args) throws Exception {
        new FileUploadApplication().run(args);
    }

    @Override
    public void run(BasicConfiguration basicConfiguration, Environment environment) {
        //register classes
        try {
            FileEncryptionService fileEncryptionService = FileEncryptionService.getInstance();
            IDataStorage dataStorage = new MemoryDataStorage(fileEncryptionService);
            FileUploadResource fileUploadResource = new FileUploadResource(basicConfiguration, dataStorage);

            environment
                    .jersey()
                    .register(fileUploadResource);
            environment.jersey().register(new MultiPartBundle());
            environment.jersey().register(MultiPartFeature.class);
        } catch (FileUploadException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initialize(Bootstrap<BasicConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }
}
