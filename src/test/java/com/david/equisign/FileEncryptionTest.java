package com.david.equisign;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEncryptionTest {

    @Test
    public void encryptAndDecryptFile() throws Exception {
        FileEncryptionService fileEncryption = FileEncryptionService.getInstance();
        File fileIn = new File("src/test/resources/a.txt");
        File fileEncrypted = new File("src/test/resources/b.txt");
        fileEncryption.encrypt(fileIn,fileEncrypted);
        File fileDecrypted = new File("src/test/resources/c.txt");
        fileEncryption.decrypt(fileEncrypted ,fileDecrypted);
        byte [] bFrom =  Files.readAllBytes(Path.of("src/test/resources/a.txt"));
        byte [] bTo =  Files.readAllBytes(Path.of("src/test/resources/c.txt"));
        Assert.assertEquals(new String(bFrom), new String(bTo));
    }
}
