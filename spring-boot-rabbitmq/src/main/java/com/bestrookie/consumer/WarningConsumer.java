package com.bestrookie.consumer;

import com.bestrookie.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author bestrookie
 * @date 2022/3/24 17:20
 */
@Component
@Slf4j
public class WarningConsumer {
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message){
        String msg = new String(message.getBody());
        log.info("报警发现不可用交换机消息：{}",msg);
    }
}
