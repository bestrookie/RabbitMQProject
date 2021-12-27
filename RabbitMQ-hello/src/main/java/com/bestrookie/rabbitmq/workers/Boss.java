package com.bestrookie.rabbitmq.workers;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2021/12/26 14:25
 */
public class Boss {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        System.out.println("请输入消息：");
        Channel channel = RabbitUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            System.out.println("请输入消息：");
            String msg = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("发送消息完成-->"+msg);
        }
    }
}
