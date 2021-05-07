package com.david.equisign;

/**
 * Functional Exception throw when data is not found
 */
public class DataNotFoundException extends Exception{

    public DataNotFoundException (String message) {
        super(message);
    }
}
