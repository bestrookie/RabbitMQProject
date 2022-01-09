package com.bestrookie.rabbitmq.seven;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/9 17:27
 */
public class ReceiveLogsTopic01 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        String queueName = "Q1";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName, EXCHANGE_NAME,"*.orange.*");
        System.out.println("Q1等待接收消息");
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("Q1接收消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
        };
        channel.basicConsume(queueName,false,deliverCallback, cancelCallback->{});
    }
}
