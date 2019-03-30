package com.zan.hu.file.async;

import com.zan.hu.file.dao.ImageMapper;
import com.zan.hu.file.domin.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AsyncTask {

    @Autowired
    private ImageMapper imageMapper;

    @Async("taskExecutor")
    public void create(MultipartFile file, String objectId) {
        Image image = new Image();
        image.setUserId(0L);
        image.setPath(objectId);
        image.setFormat(file.getContentType());
        image.setFileName(getFileName(file.getOriginalFilename()));
        imageMapper.insertSelective(image);
    }


    private String getFileName(String originalFilename) {
        int temp = originalFilename.lastIndexOf(".");
        int length = originalFilename.length();
        String fileName = originalFilename.substring(temp + 1, length);
        return fileName;
    }
}
