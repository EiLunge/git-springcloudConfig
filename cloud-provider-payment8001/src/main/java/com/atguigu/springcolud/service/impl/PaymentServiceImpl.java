package com.atguigu.springcolud.service.impl;


import com.atguigu.springcolud.dao.PaymentDao;

import com.atguigu.springcolud.entities.Payment;
import com.atguigu.springcolud.service.PaymentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource  //java自带自动装配
    private PaymentDao paymentDao;
    //新增
    public int add(Payment payment){
        return paymentDao.add(payment);
    }

    //查询
    public Payment getPaymentById(Long id){
      return paymentDao.getPaymentById(id);
    }
}
