package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl  implements PaymentService {

    @Override
    public String paymentInfo_OK(Integer id) {

        return "线程池 ："+Thread.currentThread().getName()+"paymentInfo_OK"+id ;

    }

    @Override
    //声明一个兜底的方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            //这个是规定三秒钟以内的业务逻辑为正常的
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
//        //int a=10/0;
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        return "线程池 ："+Thread.currentThread().getName()+"paymentInfo_TimeOut"+id+"耗时："+id;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"8001系统错误  --别点了！！！！,id"+id+"o(╥﹏╥)o"+"傻x";
    }
    //_______________________________服务熔断
    @HystrixCommand(fallbackMethod = "paymentCiruitBreaker_FallBack",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value ="true" ),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value ="10" ),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value ="10000" ),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value ="60" ),//失败率达到多少后跳闸
    })
    public String paymentCiruitBreaker(@PathVariable("id") Integer id){
        if (id<0){
            throw new RuntimeException("id不能为负数！！！");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"调用成功，流水号为："+serialNumber;
    }
    public String paymentCiruitBreaker_FallBack(@PathVariable("id") Integer id){
        return "id不能为负数，请稍后再试"+id;
    }


}
