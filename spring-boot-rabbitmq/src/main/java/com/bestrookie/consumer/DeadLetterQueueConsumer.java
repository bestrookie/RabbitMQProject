package com.bestrookie.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author bestrookie
 * @date 2022/1/26 4:02 下午
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {
    @RabbitListener(queues = "dead_queue")
    public void receiveDead(Message msg, Channel channel) throws Exception{
        String message =  new String(msg.getBody());
        log.info("当前时间：{},收到死信队列的消息：{}",new Date(),msg);
    }
}
