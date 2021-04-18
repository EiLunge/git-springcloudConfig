package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //自动刷新
public class ConfigClientContorller {
    @Value("${config.info}")
    private String configInfo;

    @Value("server.port")
    private String serverPort;

    @GetMapping(value = "configInfo3366")
    public String getConfigInfo(){
        return "serverPort:"+serverPort+"configInfo"+configInfo;
    }
}
