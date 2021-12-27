package com.bestrookie.rabbitmq.product;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @author bestrookie
 * @version 1.0
 * @date 2021/12/19 16:11
 */
public class Producer {
    public static final String QUENUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.102.118.110");
        factory.setUsername("rookie");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 生成一个队列
         * 1、队列名称
         * 2、队列里面的消息是否持久化，默认情况消息存储在内存中
         * 3、该队列是否只提供一个消费者进行消费 是否进行消息共享，true可以多个消费者消费，false 只能有一个消费者
         * 4、是否自动删除，最后一个消费者断开链接以后，该队列是否自动删除，true自动删除 false 不自动删除
         * 5、其他参数
         */
        channel.queueDeclare(QUENUE_NAME,false,false,false,null);
        String msg = "hello world2";
        /**
         *
         * 发送一条消息
         * 1、发送到哪个交换机
         * 2、路由的Key值是哪个 本次是队列的名称
         * 3、其他参数
         * 4、发送消息的消息体
         */
        channel.basicPublish("",QUENUE_NAME,null,msg.getBytes());
        System.out.println("消息发送成功=====");

    }
}
