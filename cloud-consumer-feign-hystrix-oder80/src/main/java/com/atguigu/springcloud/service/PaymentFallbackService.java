package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentHystrix(Integer id) {
        return "-------------PaymentFallbackService fall back";
    }

    @Override
    public String paymentHystrixTimeout(Integer id) {
        return "PaymentFallbackService-------------------TimeOut";
    }
}
