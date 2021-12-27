package com.bestrookie.rabbitmq.three;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2021/12/26 21:03
 */
public class Product {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtils.getChannel();

        channel.queueDeclare(TASK_QUEUE_NAME,false,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            channel.basicPublish("",TASK_QUEUE_NAME,null,msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功："+msg);
        }

    }
}
