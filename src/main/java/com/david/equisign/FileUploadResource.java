package com.david.equisign;


import org.glassfish.jersey.media.multipart.*;

import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/equisign")
public class FileUploadResource {

    private BasicConfiguration configuration;
    private IDataStorage dataStorage;

    private static final Logger LOG = Logger.getGlobal();

    public FileUploadResource (BasicConfiguration configuration, IDataStorage dataStorage) {
        this.configuration = configuration;
        this.dataStorage = dataStorage;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/files")
    public Response upload ( @FormDataParam("fileData")FormDataContentDisposition contentDisposition,
                             @FormDataParam("fileData")InputStream inputStream) {

       try {
           LOG.log(Level.INFO,"Receive file " + contentDisposition.getFileName());
          FileInfo fileInfo = dataStorage.saveFile(inputStream,configuration.getUploadsDir(),contentDisposition.getFileName());
           return Response.ok(fileInfo.getId()).build();
       } catch (IOException ex) {
           return Response.serverError().build();
       }

    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/files")
    public Response download (@QueryParam("id")String idFile ) {
        try {
            FileInfo fileInfo = dataStorage.readFile(idFile);
            return Response.ok(fileInfo.getFile(),MediaType.APPLICATION_OCTET_STREAM).header("content-disposition","attachment; filename = "+ fileInfo.getName()).build();
        } catch (FileNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (IOException ex) {
            return Response.serverError().build();
        }

    }


}
