package com.david.equisign;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

import java.io.File;

public class FileEncryptionTest {

    @Test
    public void encryptAndDecryptFile() throws Exception {
        FileEncryption fileEncryption = new FileEncryption();
        fileEncryption.makeKey();
        File fileIn = new File("src/test/resources/a.txt");
        File fileOut = new File("src/test/resources/b.txt");
        fileEncryption.encrypt(fileIn,fileOut);
        File fileDecrypted = new File("src/test/resources/c.txt");
        fileEncryption.decrypt(fileOut,fileDecrypted);
    }
}
