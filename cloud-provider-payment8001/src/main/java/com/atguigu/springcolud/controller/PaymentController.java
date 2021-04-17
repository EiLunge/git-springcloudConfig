package com.atguigu.springcolud.controller;

import com.atguigu.springcloud.entities.CommonResult;

import com.atguigu.springcolud.entities.Payment;
import com.atguigu.springcolud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        final List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info(service);
        }
        final List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance serviceInstance:instances) {
            log.info(serviceInstance.getInstanceId()+"\t"+serviceInstance.getUri()+"\t"+
                    serviceInstance.getHost()+"\t"+serviceInstance.getPort());
        }
        return this.discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }


    /**
     * 测试 OpenFeign超时控制
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentTimeout(){
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }
}
