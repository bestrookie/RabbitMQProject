package com.bestrookie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author bestrookie
 * @date 2022/1/26 3:01 下午
 */
@RestController
@Slf4j
public class RabbitMQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostMapping("/send")
    public void sendMsg(@RequestBody String msg){
      log.info("当前时间：{}，发送一条消息",new Date());
      rabbitTemplate.convertAndSend("normal","XA","消息来自ttl为10秒的队列："+ msg);
        rabbitTemplate.convertAndSend("normal","XB","消息来自ttl为40秒的队列："+ msg);
    }
}
