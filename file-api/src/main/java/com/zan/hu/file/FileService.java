package com.zan.hu.file;

import com.zan.hu.file.dto.FileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    /**
     * upload single file
     *
     * @param file
     * @return
     */
    String singleUpload(MultipartFile file);

    /**
     * upload multi file
     *
     * @param files
     * @return
     */
    List<String> multiUpload(MultipartFile[] files);

    /**
     * create a {@link FileDto} instance
     *
     * @param objectId
     * @return
     */
    FileDto getByObjectId(String objectId);

    /**
     * delete the entity with the given id
     *
     * @param objectId
     */
    void delete(String objectId);

    /**
     * delete all entity by the repository
     */
    void batchDelete(List<String> objectIds);

    /**
     * download file
     *
     * @param objectId
     * @return
     */
    ResponseEntity download(String objectId);

    /**
     * preview file
     *
     * @param objectId
     * @return
     */
    ResponseEntity preview(String objectId);
}
