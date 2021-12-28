package com.bestrookie.rabbitmq.three;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2021/12/26 21:03
 */
public class CustomerOne {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtils.getChannel();
        System.out.println("C1等待时间短");
        DeliverCallback deliverCallback = (consumerTag,msg)->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接收消息："+new String(msg.getBody()));
            //手动应答
            channel.basicAck(msg.getEnvelope().getDeliveryTag(),false);
        };

        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println("消费信息被取消");
        };
        //采用手动应答
        //接收方式设置 0轮询 1不公平分发 其他欲取值
        channel.basicQos(2);
        channel.basicConsume(TASK_QUEUE_NAME, false,deliverCallback,cancelCallback);
    }
}
