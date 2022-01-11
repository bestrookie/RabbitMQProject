package com.bestrookie.rabbitmq.eight;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/11 22:25
 */
public class Consumer02 {
    public static final String DEAL_QUEUE = "queue_exchange";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        System.out.println("等待接收消息.....");
        DeliverCallback deliverCallback = (tag, msg)->{
            System.out.println("02接收消息：" + new String(msg.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume(DEAL_QUEUE,true,deliverCallback,cancel->{});

    }
}
