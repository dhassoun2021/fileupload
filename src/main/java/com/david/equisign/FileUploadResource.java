package com.david.equisign;


import org.glassfish.jersey.media.multipart.*;

import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;

@Path("/equisign")
public class FileUploadResource {

    private BasicConfiguration configuration;
    private IDataStorage dataStorage;

    public FileUploadResource (BasicConfiguration configuration, IDataStorage dataStorage) {
        this.configuration = configuration;
        this.dataStorage = dataStorage;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/upload")
    public Response upload ( @FormDataParam("fileData")FormDataContentDisposition contentDisposition,
                                                                  @FormDataParam("fileData")InputStream inputStream) {

       try {
           //String pathFile = configuration.getUploadsDir() + "/" + contentDisposition.getFileName();
          // writeToFile(inputStream, pathFile);
          FileInfo fileInfo = dataStorage.saveFile(inputStream,configuration.getUploadsDir(),contentDisposition.getFileName());
           return Response.ok(fileInfo.getId()).build();
       } catch (IOException ex) {
           return Response.serverError().build();
       }

    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/data")
    public Response download (@QueryParam("id")String idFile ) {
        try {
            FileInfo fileInfo = dataStorage.readFile(idFile);
            File file = new File (fileInfo.getPath());
            return Response.ok(file,MediaType.APPLICATION_OCTET_STREAM).header("content-disposition","attachment; filename = "+ fileInfo.getName()).build();
        } catch (FileNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (IOException ex) {
            return Response.serverError().build();
        }

    }


}
