package com.zan.hup.file;

import com.zan.hup.file.dto.FileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "file-server")
public interface FileService {

    @PostMapping("/api/file/upload")
    String singleFileUpload(@RequestPart("file") MultipartFile file);

    @PostMapping("/api/file/uploads")
    List<String> multiFileUpload(@RequestPart("files") MultipartFile[] files);

    @GetMapping("/api/file/objectId")
    FileDto getFileByObjectId(@RequestParam("objectId") String objectId);

    @DeleteMapping("/api/file")
    void deleteFile(String objectId);

    @GetMapping("/api/file/download")
    ResponseEntity download(@RequestParam("objectId") String objectId);

    @GetMapping("/api/file/preview")
    ResponseEntity preview(@RequestParam("objectId") String objectId);
}
