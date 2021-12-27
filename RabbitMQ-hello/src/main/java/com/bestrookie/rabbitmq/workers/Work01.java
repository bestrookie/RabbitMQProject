package com.bestrookie.rabbitmq.workers;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2021/12/25 20:21
 */
public class Work01 {
    public static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtils.getChannel();
        //消息的接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("消息被接收："+new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println(consumerTag+" 消息接收失败，取消消息");
        };
        System.out.println("C等待消费");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
