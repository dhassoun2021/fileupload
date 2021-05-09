package com.david.equisign;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEncryptionTest {

    private static final String PATH_ORIGIN_FILE = "src/test/resources/a.txt";

    private static final String PATH_ENCRYPTED_FILE = "src/test/resources/file_encrypted.txt";

    private static final String PATH_DECRYPTED_FILE = "src/test/resources/file_decrypted.txt";
    @Test
    public void encryptAndDecryptFile() throws Exception {
        FileEncryptionService fileEncryption = FileEncryptionService.getInstance();
        FileInputStream fileIn = new FileInputStream(PATH_ORIGIN_FILE);
        File fileEncrypted = new File(PATH_ENCRYPTED_FILE);
        InputStream inputStream = fileEncryption.cipher(fileIn);
        FileOutputStream outputStreamEncrypted = new FileOutputStream(fileEncrypted);
        CopyUtil.copy(inputStream,outputStreamEncrypted);
        File fileDecrypted = new File(PATH_DECRYPTED_FILE);
        FileInputStream fileInputStreamEncrypted = new FileInputStream(fileEncrypted);
        InputStream stream = fileEncryption.decipher(fileInputStreamEncrypted);
        FileOutputStream fileOutputStreamDecrypted = new FileOutputStream(fileDecrypted);
        CopyUtil.copy(stream,fileOutputStreamDecrypted);
        byte [] bFrom =  Files.readAllBytes(Path.of(PATH_ORIGIN_FILE));
        byte [] bTo =  Files.readAllBytes(Path.of(PATH_DECRYPTED_FILE));
        fileInputStreamEncrypted.close();
        outputStreamEncrypted.close();
        fileOutputStreamDecrypted.close();
        Assert.assertEquals(new String(bFrom), new String(bTo));
    }

    @After
    public void clean () {
      File fileEncrypted = new File (PATH_ENCRYPTED_FILE);
      fileEncrypted.delete();

      File fileDecrypted = new File (PATH_DECRYPTED_FILE);
      fileDecrypted.delete();

    }


}
