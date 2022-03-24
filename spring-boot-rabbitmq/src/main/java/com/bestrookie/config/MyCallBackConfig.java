package com.bestrookie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author bestrookie
 * @date 2022/3/24 15:36
 */
@Slf4j
@Component
public class MyCallBackConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 消息未达到交换机时消息回调
     * @param correlationData 保存回调消息的ID以及相关信息
     * @param b 是否收到
     * @param s 原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData!=null ? correlationData.getId() : "";
        if (b){
            log.info("消息发送成功,id为：{}",id);
        }else {
            log.info("消息发送失败,id为:{},原因为：{}",id,s);
        }
    }

    /**
     * 路由不可达时将消息返回给生产者
     * @param returnedMessage 消息的相关参数
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息：{}，被交换机：{}退回，退回原因：{}，路由key：{}",returnedMessage.getMessage()
                ,returnedMessage.getExchange(),returnedMessage.getReplyText(),
                returnedMessage.getRoutingKey());
    }
}
