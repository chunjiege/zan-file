package com.zan.hu.file.model.request;

import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Author hupeng
 * @Date 2019-05-20 18:29
 * @Description todo
 **/
@Data
public class BatchDeleteRequest extends BaseRequest {
    /**
     * 文件id
     */
    List<String> objectIds;
}
