package com.david.equisign;

/**
 * Exception to manage technical errors
 */
public class FileUploadException extends Exception{

    public FileUploadException (String message) {
        super(message);
    }
}
