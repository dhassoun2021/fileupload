package com.david.equisign;

import java.io.*;
import java.security.*;


import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * Utility class for encrypting/decrypting files.
 */
public class FileEncryptionService {

    public static final int AES_Key_Size = 256;

    private Cipher  aesCipher;
    private byte[] aesKey;
    private SecretKeySpec aeskeySpec;

    private static FileEncryptionService _instance = null;

    /**
     * Constructor: creates ciphers
     */
    private FileEncryptionService() throws FileUploadException {
        try {
            aesCipher = Cipher.getInstance("AES");
            makeKey();
     } catch (GeneralSecurityException ex) {
         throw new FileUploadException(ex.getMessage());
     }
    }

    public static FileEncryptionService getInstance() throws FileUploadException {
        if (_instance == null) {
            _instance = new FileEncryptionService();
        }
        return _instance;
    }

    /**
     * Creates a new AES key
     */
    private void makeKey() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(AES_Key_Size);
        SecretKey key = kgen.generateKey();
        aesKey = key.getEncoded();
        aeskeySpec = new SecretKeySpec(aesKey, "AES");
    }


    /**
     * Encrypts and then copies the contents of a given file.
     */
    public void encrypt(File in, File out)  throws FileUploadException {
        CipherOutputStream os = null;
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);
            FileInputStream is = new FileInputStream(in);
            os = new CipherOutputStream(new FileOutputStream(out), aesCipher);
            copy(is, os);
        } catch (IOException | InvalidKeyException ex) {
            throw new FileUploadException(ex.getMessage());
        }finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException ex) {
                throw new FileUploadException(ex.getMessage());
            }
        }
    }

    public void encrypt(InputStream in, File out) throws FileUploadException {
        CipherOutputStream os = null;
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);
            os = new CipherOutputStream(new FileOutputStream(out), aesCipher);
            copy(in, os);
        } catch (IOException | InvalidKeyException ex) {
            throw new FileUploadException(ex.getMessage());
        }finally {
            try {
                os.close();
            } catch (IOException ex) {
                throw new FileUploadException(ex.getMessage());
            }
        }
    }

    /**
     * Decrypts and then copies the contents of a given file.
     */
    public void decrypt(File in, File out) throws FileUploadException {
        CipherInputStream is = null;
        FileOutputStream os = null;
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, aeskeySpec);
            is = new CipherInputStream(new FileInputStream(in), aesCipher);
            os = new FileOutputStream(out);
            copy(is, os);
        } catch (IOException | InvalidKeyException ex) {
            throw new FileUploadException(ex.getMessage());
        } finally {

            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException ex) {
                throw new FileUploadException(ex.getMessage());
            }
        }
    }

    /**
     * Copies a stream.
     */
    private void copy(InputStream is, OutputStream os) throws IOException {
        int i;
        byte[] b = new byte[1024];
        while((i=is.read(b))!=-1) {
            os.write(b, 0, i);
        }
    }
}
