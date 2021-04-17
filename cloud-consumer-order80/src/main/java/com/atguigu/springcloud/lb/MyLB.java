package com.atguigu.springcloud.lb;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer {

    //原子类
    private AtomicInteger atomicInteger =new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        do{
            current = atomicInteger.get();
            //第一次进来 current 不可能大于 0 所以执行 : 后也就是false的
            next = current >= 100 ? 0 :current + 1;
            //第一次 current 是 0  next 变为 1
            //compareAndSet方法是  第一个参数放入时候 是零  do结束后还是 0 就把零 换成 next 也就是1
            //这个方法返回值是boolean类型
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("第几次："+next);
        return next;
    }

    /**
     * 实现负载均衡算法  接口第几次请求数 % 服务器集群总数 =实际调用服务器位置下标 ，重启服务器后 计数从1开始
     * @param serviceInstances
     * @return
     */
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
