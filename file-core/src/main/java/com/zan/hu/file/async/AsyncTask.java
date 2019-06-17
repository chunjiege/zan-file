package com.zan.hu.file.async;

import com.zan.hu.CommonThreadLocal;
import com.zan.hu.CurrentRelatedInfo;
import com.zan.hu.file.entity.Image;
import com.zan.hu.file.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTask {

    @Autowired
    private ImageMapper imageMapper;

    @Async("taskExecutor")
    public void create(String originalFilename, String contentType, String objectId) {
        CurrentRelatedInfo currentRelatedInfo = CommonThreadLocal.get();
        Image image = new Image();
        image.setUserId(currentRelatedInfo.getUser().getId());
        image.setObjectId(objectId);
        image.setFormat(contentType);
        image.setFileName(getFileName(originalFilename));
        imageMapper.insertSelective(image);
    }


    private String getFileName(String originalFilename) {
        int temp = originalFilename.lastIndexOf(".");
        int length = originalFilename.length();
        String fileName = originalFilename.substring(temp + 1, length);
        return fileName;
    }
}
