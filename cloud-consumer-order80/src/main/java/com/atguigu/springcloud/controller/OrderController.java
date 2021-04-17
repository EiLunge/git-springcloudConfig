package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    //public static  final String PAYMENT_URL="http://localhost:8001";   //单机状态写死url
    public static  final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/add")
    public CommonResult<Payment> add(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/add",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/addpostforentity")
    public CommonResult<Payment> add2(Payment payment){
        /**/
        return restTemplate.postForEntity(PAYMENT_URL+"/payment/add",payment,CommonResult.class).getBody();
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        /*返回对象为ResponseEntity对象，包含了响应中的一些重要信息*/
       // return restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class).getBody();
        /*返回对象为响应体中数据转化成的对象，就是JSON*/
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
    @GetMapping("/consumer/payment/getforentity/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable("id")Long id){
        /*返回对象为ResponseEntity对象，包含了响应中的一些重要信息 需要getBody()*/
        ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (forEntity.getStatusCode().is2xxSuccessful()){
            return forEntity.getBody();
        }else {
            return new CommonResult<>(444,"失败");
        }
        /*返回对象为响应体中数据转化成的对象，就是JSON*/
        //return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    /**
     * 自己写的负载均衡算法
     * @return
     */
    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances =discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <=0){
            return null;
        }
        ServiceInstance instance = loadBalancer.instance(instances);
        URI url = instance.getUri();
        return restTemplate.getForObject(url+"/payment/lb",String.class);
    }
}
