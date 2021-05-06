package com.david.equisign;

import java.util.Optional;

public interface IFileInfoDao {

    void save(FileInfo fileInfo);

    Optional<FileInfo> read(String idFile);
}
