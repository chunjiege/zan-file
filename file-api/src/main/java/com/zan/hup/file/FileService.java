package com.zan.hup.file;

import com.zan.hup.file.dto.FileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "file-server")
@RequestMapping("/api/file")
public interface FileService {

    @PostMapping("/upload")
    String singleFileUpload(@RequestParam("file") MultipartFile multipartFile);

    @PostMapping("/uploads")
    List<String> multiFileUpload(@RequestParam("files") MultipartFile[] multipartFiles);

    @GetMapping("/objectId")
    FileDto getFileByObjectId(String objectId);

    @DeleteMapping
    void deleteFile(String objectId);

    @GetMapping("/download")
    ResponseEntity download(@RequestParam("objectId") String objectId);

    @GetMapping("/preview")
    ResponseEntity preview(@RequestParam("objectId") String objectId);
}
