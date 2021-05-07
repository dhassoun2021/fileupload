package com.david.equisign;

import java.util.Optional;

/**
 * Expose operation to save and get information about file uploaded
 */
public interface IFileInfoDao {

    /**
     * Save file information
     * @param fileInfo
     */
    void save(FileInfo fileInfo);

    /**
     *
     * @param idFile id of file
     * @return file information
     */
    Optional<FileInfo> read(String idFile);
}
