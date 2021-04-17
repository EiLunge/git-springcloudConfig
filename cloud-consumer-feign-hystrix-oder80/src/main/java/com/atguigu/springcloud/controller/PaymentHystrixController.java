package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalHystrixTimeoutFallbackMethod")
public class PaymentHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value ="/conseumer/hystrix/ok/{id}")
    public String paymentHystrix(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentHystrix(id);
        return result;
    }
    @GetMapping(value ="/consumer/hystrix/time/{id}")
//    @HystrixCommand(fallbackMethod = "paymentHystrixTimeoutFallbackMethod",commandProperties={
//            //设置多场时间内启动生效
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
//    })
    @HystrixCommand
    public String paymentHystrixTimeout(@PathVariable("id") Integer id){
        System.out.println("进入了方法");
        String s = paymentHystrixService.paymentHystrixTimeout(id);
        System.out.println("调用8001成功");
        return s;
    }

    public String paymentHystrixTimeoutFallbackMethod(@PathVariable("id") Integer id){
        return "我是80.对方支付系统繁忙，请稍后再试！！！";
    }
    //全局fallback
    public String paymentGlobalHystrixTimeoutFallbackMethod(){
        return "出错了不知道为啥..........";
    }
}
