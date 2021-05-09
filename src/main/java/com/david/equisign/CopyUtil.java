package com.david.equisign;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class CopyUtil {

    /**
     * Copies a stream.
     */
    public static void copy(InputStream is, FileOutputStream os) throws IOException {
        int i;
        byte[] b = new byte[1024];
        while((i=is.read(b))!=-1) {
            os.write(b, 0, i);
        }
    }

}
