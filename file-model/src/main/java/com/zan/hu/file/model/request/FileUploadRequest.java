package com.zan.hu.file.model.request;

import lombok.Data;

import java.io.InputStream;

/**
 * @version 1.0
 * @Author hupeng
 * @Date 2019-06-17 15:28
 * @Description todo
 **/
@Data
public class FileUploadRequest extends BaseRequest {
    String originalFilename;
    String contentType;
    InputStream inputStream;
}
