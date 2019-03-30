package com.zan.hu.file.domin;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`image`")
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
     * 文件路径
     */
    private String path;

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
    @Column(name = "created_at")
    private Date createdAt;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作者
     *
     * @return user_id - 操作者
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置操作者
     *
     * @param userId 操作者
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取文件路径
     *
     * @return path - 文件路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置文件路径
     *
     * @param path 文件路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取文件名称
     *
     * @return file_name - 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取存储格式
     *
     * @return format - 存储格式
     */
    public String getFormat() {
        return format;
    }

    /**
     * 设置存储格式
     *
     * @param format 存储格式
     */
    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    /**
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除
     *
     * @param isDelete 是否删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}