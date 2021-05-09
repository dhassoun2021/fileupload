package com.david.equisign;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public final class CopyUtil {

    private static final int BUFFER_SIZE = 1024;
    /**
     * Copies a stream.
     */
    public static void copy(InputStream is, FileOutputStream os) throws IOException {
        int i;
        byte[] b = new byte[BUFFER_SIZE];
        while((i=is.read(b))!=-1) {
            os.write(b, 0, i);
        }
    }

}
