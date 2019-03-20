package com.zan.hup.file.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version 1.0
 * @author: hupeng
 * @create: 2018-10-06 21:16
 * @description:
 **/
@FeignClient
@RequestMapping("/api/file")
public interface FileService {

    @PostMapping("/single")
    String singleFileUpload(MultipartFile multipartFile);

    @PostMapping("/multi")
    List<String> multiFileUpload(MultipartFile[] multipartFiles);

    @DeleteMapping
    void deleteFile(String objectId);

    @GetMapping("/download")
    ResponseEntity download(@RequestParam("objectId") String objectId);

    @GetMapping("/preview")
    ResponseEntity preview(@RequestParam("objectId") String objectId);
}
