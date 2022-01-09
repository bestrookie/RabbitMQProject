package com.bestrookie.rabbitmq.six;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/8 21:35
 */
public class ReceivelDirect01 {
    public static final String EXCHANE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        channel.exchangeDeclare(EXCHANE_NAME,BuiltinExchangeType.DIRECT);
        channel.queueDeclare("console",false,false,false,null);
        channel.queueBind("console",EXCHANE_NAME,"info");
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("接收消息 消费者01："+new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume("console",true,deliverCallback, cancelCallback->{});

    }

}
