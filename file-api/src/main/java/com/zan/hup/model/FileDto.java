package com.zan.hup.model;

import lombok.Data;

import java.io.ByteArrayInputStream;
import java.util.Date;

@Data
public class FileDto {

    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件类型
     */
    private String contentType;
    private Long length;
    private Integer chunkSize;
    private Date uploadDate;
    private String md5;
    /**
     * 文件内容
     */
    private ByteArrayInputStream content;
}
