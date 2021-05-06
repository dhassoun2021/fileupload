package com.david.equisign;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FileInfoDao implements IFileInfoDao{

    private static FileInfoDao fileInfoDao = new FileInfoDao();


    private Map<String,FileInfo> datas = new ConcurrentHashMap<>();

    private FileInfoDao () {

    }

    public void save (FileInfo fileInfo) {
        String id = UUID.randomUUID().toString();
        fileInfo.setId(id);
        datas.put(id,fileInfo);
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
