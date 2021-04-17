package com.atguigu.springcloud.controller;

import cn.hutool.log.Log;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentHytrixController {
    @Resource
    private PaymentService paymentService;

    @Value("server.port")
    private String serverPort;

    @GetMapping(value ="payment/hystrix/ok/{id}")
    public String paymentHystrix(@PathVariable("id") Integer id){
        return paymentService.paymentInfo_OK(id);
    }
    @GetMapping(value ="payment/hystrix/time/{id}")
    public String paymentHystrixTimeout(@PathVariable("id") Integer id){
        return paymentService.paymentInfo_TimeOut(id);
    }


    //——————————————————————服务熔断
    @GetMapping(value = "/payment/circuit/{id}")
    public String paymentCircuit(@PathVariable("id") Integer id){
        String s = paymentService.paymentCiruitBreaker(id);
        log.info("@@@@@"+s);
        return s;
    }

}
