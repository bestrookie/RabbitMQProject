package com.bestrookie.rabbitmq.product;

import com.rabbitmq.client.*;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2021/12/19 20:01
 */
public class Consumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("7.102.118.110");
        factory.setUsername("rookie");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //接收消息
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("未成功消费回调->" +
                    new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println("消费被取消");
        };
        /**
         * 消费者接收消息
         * 1、消费哪个队列
         * 2、消费成功之后是否自动应答 true代表自动应答 false代表手动应答
         * 3、接收消息的回调
         * 4、消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME,deliverCallback,cancelCallback);
    }
}
