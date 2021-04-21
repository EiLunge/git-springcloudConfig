package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class SentinelController {

    @GetMapping(value = "/get/a")
    public String testA(){
        log.info(Thread.currentThread().getName());
        return "this is testA";

    }
    @GetMapping(value = "/get/b")
    public String testB(){
        return "this is testB";
    }
    @GetMapping(value = "/get/d")
    public String testD(){
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "this is testD";
    }
    @GetMapping(value = "/test/hot")
    @SentinelResource(value = "testhot",blockHandler = "deal_testhot")
    public String testHot(@RequestParam(value = "p1",required = false) String p1,
                          @RequestParam(value = "p2",required = false) String p2    ){
        return "testhot";
    }

    public String deal_testhot(String p1, String p2, BlockException e){
        return "deal_testhot兜底方法";
    }
}
