package com.zan.hu.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @version 1.0
 * @Author hupeng
 * @Date 2019-03-31 13:30
 * @Description todo
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConf extends com.zan.hu.jwt.ResourceServerConf {

    private static final String RESOURCE_ID = "file-server";

    @Override
    public String resourceId() {
        return RESOURCE_ID;
    }
}
