package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@EnableBinding(Sink.class)
public class ReceiveMessageListenController {


    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)//获取消费消息监听
    public void get(Message<String> message){
        System.out.println("消费者8003"+message.getPayload()+"server.prt"+serverPort);
    }
}
