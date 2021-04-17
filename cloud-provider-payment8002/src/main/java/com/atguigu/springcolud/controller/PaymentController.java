package com.atguigu.springcolud.controller;

import com.atguigu.springcloud.entities.CommonResult;

import com.atguigu.springcolud.entities.Payment;
import com.atguigu.springcolud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/add")
    public CommonResult add(@RequestBody Payment payment){
        int add = paymentService.add(payment);
        if (add>0){
            return new CommonResult(200,"插入成功,serverPort "+serverPort ) ;
        }else {
            return new CommonResult(444,"插入失败" ) ;
        }
    }

    @GetMapping (value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("查询到的"+paymentById);
        if (paymentById != null){
            return new CommonResult(200,"查询成功,serverPort "+serverPort ,paymentById) ;
        }else {
            return new CommonResult(444,"查询失败" ) ;
        }

    }
    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
