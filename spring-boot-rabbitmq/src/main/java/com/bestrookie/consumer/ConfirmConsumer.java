package com.bestrookie.consumer;

import com.bestrookie.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author bestrookie
 * @date 2022/3/24 15:17
 * @description 高级
 */
@Component
@Slf4j
public class ConfirmConsumer {
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message){
        String  msg = new String(message.getBody());
        log.info("接收到的消息为：{}",msg);
    }
}
