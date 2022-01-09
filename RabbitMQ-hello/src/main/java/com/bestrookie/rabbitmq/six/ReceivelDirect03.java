package com.bestrookie.rabbitmq.six;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/8 21:35
 */
public class ReceivelDirect03 {
    public static final String EXCHANE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        channel.exchangeDeclare(EXCHANE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("disk",false,false,false,null);
        channel.queueBind("disk",EXCHANE_NAME,"error");
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("接收消息 消费者03："+new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume("disk",true,deliverCallback, cancelCallback->{});

    }

}
