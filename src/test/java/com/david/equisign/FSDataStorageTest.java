package com.david.equisign;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;


public class FSDataStorageTest {

    private  FSDataStorage dataStorage;

    @Mock
    private BasicConfiguration basicConfigurationMock;

    @Before
    public void init () throws Exception {
        MockitoAnnotations.initMocks(this);
        FileEncryptionService encryptionService = FileEncryptionService.getInstance();
        Mockito.when(basicConfigurationMock.getUploadsDir()).thenReturn("src/test/resources/input");
        Mockito.when(basicConfigurationMock.getTmpDir()).thenReturn("src/test/resources/tmp");
        dataStorage = new FSDataStorage(encryptionService, FileInfoDao.getInstance(), basicConfigurationMock);
    }

    @Test
    public void save () throws Exception {
       FileInfo fileInfo = dataStorage.saveFile(new FileInputStream("src/test/resources/input/file.txt"),"encryptedFile.txt");
       Assert.assertNotNull(fileInfo.getId());
    }

    @Test
    public void read () throws Exception{
        FileInfo fileInfo = dataStorage.saveFile(new FileInputStream("src/test/resources/input/file.txt"),"encryptedFile.txt");
        FileInfo fileInfo2 = dataStorage.readFile(fileInfo.getId());
        Assert.assertNotNull(fileInfo2);
        Assert.assertEquals(fileInfo.getId(),fileInfo2.getId());
        Assert.assertEquals(fileInfo.getPath(),fileInfo2.getPath());
    }

    @After
    public void cleanUp () {
       File file = new File("src/test/resources/input/encryptedFile.txt");
       file.delete();
        File directoryTmp = new File("src/test/resources/tmp");
        File [] files = directoryTmp.listFiles();
        if (files != null && files.length > 0) {
            for (File aFile : files) {
                aFile.delete();
            }
        }

    }
}
