package com.zan.hu.file.model;

import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Date;

@Data
public class FileDto implements Serializable {

    private static final long serialVersionUID = -4876186869496600469L;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件类型
     */
    private String contentType;
    /**
     * The length, in bytes of this file
     */
    private Long length;
    /**
     * The size, in bytes, of each data chunk of this file
     */
    private Integer chunkSize;
    /**
     * The date and time this file was added to GridFS
     */
    private Date uploadDate;
    /**
     * 文件内容
     */
    private ByteArrayInputStream content;
}
