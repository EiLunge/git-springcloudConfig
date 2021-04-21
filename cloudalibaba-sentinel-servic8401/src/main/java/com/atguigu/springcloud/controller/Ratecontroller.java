package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ratecontroller {

    @GetMapping(value = "/byResuorce")
    @SentinelResource(value = "byResurce",blockHandler = "handlerEx")
    public CommonResult byCommonResult(){
        return new CommonResult(200,"按资源名称限流测试",new Payment(2020L,"seral001"));
    }
    public CommonResult handlerEx(BlockException e){
        return new CommonResult(444,e.getClass().getCanonicalName()+"服务不可用");
    }
}
