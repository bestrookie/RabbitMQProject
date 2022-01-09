package com.bestrookie.rabbitmq.filve;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/8 20:46
 */
public class ReceiveLogs02 {
    public static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明一个临时对垒
        String queue = channel.queueDeclare().getQueue();
        //绑定交换机与队列
        channel.queueBind(queue,EXCHANGE_NAME,"1");
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("接收消息 log02 ："+ new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume(queue,true,deliverCallback,cancelCallback->{});

    }
}
