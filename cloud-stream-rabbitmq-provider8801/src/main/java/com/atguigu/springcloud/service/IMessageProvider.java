package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public interface IMessageProvider {
    public String send();
}
