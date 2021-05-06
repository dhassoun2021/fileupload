package com.david.equisign;


import org.glassfish.jersey.media.multipart.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.time.LocalDateTime;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/files")
    public Response upload ( @FormDataParam("fileData")FormDataContentDisposition contentDisposition,
                             @FormDataParam("fileData")InputStream inputStream, @Context HttpServletRequest request) {

       try {
           if (inputStream == null || contentDisposition.getFileName() == null || contentDisposition.getFileName().trim().length() == 0 ) {
               return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),"File is mandatory").build();
           }
           if (request.getContentLength() > configuration.getMaxSizeRequest()) {
               return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),"Request size is limited to " + configuration.getMaxSizeRequest() + "octets").build();
           }
            LOG.log(Level.INFO,"Receive file " + contentDisposition.getFileName());
            FileInfo fileInfo = dataStorage.saveFile(inputStream,contentDisposition.getFileName());
           return Response.ok(fileInfo.getId()).build();
       } catch (FileUploadException ex) {
           LOG.log(Level.SEVERE,"Error with upload file " + ex.getMessage());
           return Response.serverError().build();
       }

    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/files/{idFile}")
    public Response download (@PathParam("idFile") String idFile ) {
        try {
            FileInfo fileInfo = dataStorage.readFile(idFile);
            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream out) throws IOException, WebApplicationException {
                    try (FileInputStream inp = new FileInputStream(fileInfo.getFile())) {
                        byte[] buff = new byte[1024];
                        int len = 0;
                        while ((len = inp.read(buff)) >= 0) {
                            out.write(buff, 0, len);
                        }
                        out.flush();
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Stream file failed", e);
                        throw new IOException("Stream error: " + e.getMessage());
                    } finally {
                        LOG.log(Level.INFO,"Remove stream file: " + fileInfo.getFile());
                        fileInfo.getFile().delete();
                    }
                }
            };

            return Response.ok(stream).header("content-disposition","attachment; filename = "+ fileInfo.getName()).build();
        } catch (FileNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (FileUploadException ex) {
            LOG.log(Level.SEVERE,"Error with download  file " + idFile + " " +ex.getMessage());
            return Response.serverError().build();
        }

    }


}
