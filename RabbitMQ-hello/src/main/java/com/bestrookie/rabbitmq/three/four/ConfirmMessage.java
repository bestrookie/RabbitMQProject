package com.bestrookie.rabbitmq.three.four;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.util.UUID;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/3 21:38
 */
public class ConfirmMessage {
    public static void main(String[] args) throws Exception{
        //单个确认
        //publishMessageSign();
        //批量确认
       publishMessageBatch();
    }
    public static void publishMessageSign() throws Exception{
        Channel channel = RabbitUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启发布确认模式
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < 1000; i++) {
            String msg = i + "";
            channel.basicPublish("",queueName,null,msg.getBytes());
            boolean flg = channel.waitForConfirms();
            if (flg){
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间:"+(end - begin));
    }

    public static void publishMessageBatch() throws Exception{
        Channel channel = RabbitUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.confirmSelect();
        channel.queueDeclare(queueName,true,false,false,null);
        long begin = System.currentTimeMillis();
        //设置多少甜确认一次
        int confirmSize = 100;
        for (int i = 1; i < 1000+1; i++) {
            String msg = i + "";
            channel.basicPublish("",queueName,null,msg.getBytes());
            if (i % confirmSize == 0){
                channel.waitForConfirms();

                    System.out.println("消息确认成功");

            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间:"+(end - begin));
    }
}
