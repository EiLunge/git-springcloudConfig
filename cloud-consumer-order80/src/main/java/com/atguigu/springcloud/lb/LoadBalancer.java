package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 接口主要收集能提供服务的机器
 */
public interface LoadBalancer {
    //ServiceInstance服务实例
    ServiceInstance instance(List<ServiceInstance> serviceInstances);
}
