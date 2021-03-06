package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.entity.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;
    @Override
    public void create(Order order) {
        log.info("------开始新建订单");
        orderDao.create(order);
        log.info("-------订单微服务开始调用库存做扣减");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("----------扣减结束");
        log.info("-----------订单微服务做账户扣减");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("----------扣减钱数结束");
        //修改订单状态 0 1 订单完成
        log.info("------修改订单状态开始");
        orderDao.update(order.getId(), 0);
        log.info("------修改订单状态结束");
        log.info("------订单结束");


    }
}
