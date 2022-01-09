package com.bestrookie.rabbitmq.six;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/8 22:42
 */
public class DirectLogs {
    public static final String EXCHANE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtils.getChannel();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            channel.basicPublish(EXCHANE_NAME,"info",null,msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完毕");
        }
    }
}
