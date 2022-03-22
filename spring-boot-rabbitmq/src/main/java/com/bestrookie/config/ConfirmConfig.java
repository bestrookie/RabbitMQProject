package com.bestrookie.config;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bestrookie
 * @Description 发布确认高级
 * @version 1.0
 * @date 2022/3/22 21:29
 */
@Configuration
public class ConfirmConfig {
    /**
     * 交换机名称
     */
    public static final String  CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    /**
     * 交换机队列名称
     */
    public static final String  CONFIRM_QUEUE_NAME = "confirm_queue";
    /**
     * RoutingKey
     */
    public static final String  CONFIRM_ROUTING_KEY = "key1";

    /**
     * 声明交换机
     * @return 直接型交换机
     */
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    /**
     * 声明队列
     * @return 队列
     */
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue, @Qualifier("confirmExchange") DirectExchange confirmChange){
        return BindingBuilder.bind(confirmQueue).to(confirmChange).with(CONFIRM_ROUTING_KEY);
    }

}
