package com.david.equisign;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Store information about file uploaded in memory
 */
public class FileInfoDao implements IFileInfoDao{

    private static FileInfoDao fileInfoDao = new FileInfoDao();


    private Map<String,FileInfo> datas = new ConcurrentHashMap<>();

    private FileInfoDao () {

    }

    public void save (FileInfo fileInfo) {
        datas.put(fileInfo.getId(),fileInfo);
    }

    public Optional <FileInfo> read (String idFile) {
        FileInfo fileInfo = datas.get(idFile);
        if (fileInfo == null) {
            return Optional.empty();
        }
        return Optional.of(fileInfo);

    }

    public static FileInfoDao getInstance() {
        return fileInfoDao;
    }


}
