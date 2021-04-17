package com.atguigu.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderConsulController {

    private static final String INVOKE_URL="http://consul-provider-payment";

    @Resource
    public RestTemplate restTemplate;

    @GetMapping("/consumer/consul")
    public String orderConsul(){
        return restTemplate.getForObject(INVOKE_URL+"/payment/consul",String.class);
    }


}
