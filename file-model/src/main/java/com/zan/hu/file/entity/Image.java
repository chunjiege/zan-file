package com.zan.hu.file.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "`image`")
@Data
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作者
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 文件id
     */
    private String objectId;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 存储格式
     */
    private String format;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 被更新时间
     */
    private Date updated;

    private static final long serialVersionUID = 1L;

}