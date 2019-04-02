package com.zan.hu.file.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @version 1.0
 * @Author hupeng
 * @Date 2019-03-31 13:30
 * @Description todo
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConf extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    private static final String RESOURCE_ID = "file-server";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore)
                .resourceId(RESOURCE_ID);
    }
}
