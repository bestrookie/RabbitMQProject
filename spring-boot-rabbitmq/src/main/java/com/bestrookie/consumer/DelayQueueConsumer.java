package com.bestrookie.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author bestrookie
 * @date 2022/2/8 5:22 下午
 */
@Slf4j
@Component
public class DelayQueueConsumer {
    @RabbitListener(queues = "delayed.queue")
    public void receiveDelayMsg(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到死信队列的消息：{}",new Date(),msg);
    }
}
