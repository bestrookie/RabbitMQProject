package com.bestrookie.rabbitmq.eight;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/11 22:12
 */
public class Product {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();

        for (int i = 0; i < 10; i++) {
            String msg = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE,"bestrookie",null,msg.getBytes());
            System.out.println("消息发送成功: "+msg);
        }
    }
}
