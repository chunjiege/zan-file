package com.zan.hup.file.async;

import com.zan.hup.file.dao.ImageMapper;
import com.zan.hup.file.domin.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTask {

    @Autowired
    private ImageMapper imageMapper;

    @Async
    public void create(Image image){
        int i = imageMapper.insertSelective(image);
    }
}
