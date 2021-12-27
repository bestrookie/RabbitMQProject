package com.bestrookie.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2021/12/25 20:07
 */
public class RabbitUtils {
    public static Channel getChannel() throws Exception{
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("47.102.118.110");
    factory.setUsername("rookie");
    factory.setPassword("123456");
    Connection connection = factory.newConnection();
    return connection.createChannel();
}
}
