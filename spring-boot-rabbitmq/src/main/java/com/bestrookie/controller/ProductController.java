package com.bestrookie.controller;

import com.bestrookie.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author bestrookie
 * @date 2022/3/24 14:48
 */
@RestController
@Slf4j
public class ProductController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/confirm/sendMessage/{message}")
    public void sendMessage(@PathVariable String message) {
        int  id = new Random().nextInt(9) + 1;
        CorrelationData correlationData = new CorrelationData(String.valueOf(id));
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY,message,correlationData);
        log.info("发送消息内容为：{}",message);
        CorrelationData correlationData2 = new CorrelationData(String.valueOf(id));
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY+"2",message+"2",correlationData2);
        log.info("发送消息2：{}",message+"2");
    }
}
