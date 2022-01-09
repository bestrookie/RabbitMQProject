package com.bestrookie.rabbitmq.filve;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/8 21:10
 */
public class EmitLog {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
            System.out.println("消息发送完毕："+msg);
        }

    }
}
