package com.zan.hup.file;

import com.zan.hup.model.FileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String singleFileUpload(MultipartFile multipartFile);

    List<String> multiFileUpload(MultipartFile[] multipartFiles);

    FileDto getFileByObjectId(String objectId);

    void deleteFile(String objectId);

    ResponseEntity download(String objectId);

    ResponseEntity preview(String objectId);
}
