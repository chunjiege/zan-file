package com.zan.hu.file.controller;

import com.zan.hu.file.FileService;
import com.zan.hu.file.dto.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version 1.0
 * @Author admin
 * @Date 2019-03-28 23:34
 * @Description todo
 **/
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload")
    public String singleUpload(@RequestPart(value = "file") MultipartFile file) {
        Assert.notNull(file, "file must not null");
        return fileService.singleUpload(file);
    }

    @PostMapping("/uploads")
    public List<String> multiUpload(@RequestPart("files") MultipartFile[] files) {
        Assert.notNull(files, "file must not null");
        return fileService.multiUpload(files);
    }

    @GetMapping("/objectId/{objectId}")
    public FileDto getByObjectId(@PathVariable("objectId") String objectId) {
        Assert.hasLength(objectId, "the given id must not null ");
        return fileService.getByObjectId(objectId);
    }

    @DeleteMapping("/{objectId}")
    public void delete(@PathVariable("objectId") String objectId) {
        Assert.hasLength(objectId, "the given id must not null ");
        fileService.delete(objectId);
    }

//    @DeleteMapping("/batch")
//    public void batchDelete(@RequestParam(value = "objectIds") List<String> objectIds) {
//        Assert.notEmpty(objectIds, "the given id must not null ");
//        fileService.batchDelete(objectIds);
//    }


    @GetMapping("/download/{objectId}")
    public ResponseEntity download(@PathVariable("objectId") String objectId) {
        Assert.hasLength(objectId, "the given id must not null ");
        return fileService.download(objectId);
    }

    @GetMapping("/preview/{objectId}")
    public ResponseEntity preview(@PathVariable("objectId") String objectId) {
        Assert.hasLength(objectId, "the given id must not null ");
        return fileService.preview(objectId);
    }
}
