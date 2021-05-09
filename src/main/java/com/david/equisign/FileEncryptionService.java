package com.david.equisign;

import java.io.*;
import java.security.*;


import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * Utility class for encrypting/decrypting files.
 */
public class FileEncryptionService {

    public static final int AES_KEY_SIZE = 256;

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
        kgen.init(AES_KEY_SIZE);
        SecretKey key = kgen.generateKey();
        aesKey = key.getEncoded();
        aeskeySpec = new SecretKeySpec(aesKey, "AES");
    }





    public InputStream cipher(InputStream in) throws FileUploadException {
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec);
            return new CipherInputStream(in, aesCipher);
        } catch (InvalidKeyException ex) {
            throw new FileUploadException(ex.getMessage());
        }
    }

    public InputStream decipher(InputStream in) throws FileUploadException {
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, aeskeySpec);
            return new CipherInputStream(in, aesCipher);
        } catch (InvalidKeyException ex) {
            throw new FileUploadException(ex.getMessage());
        }
    }

}
