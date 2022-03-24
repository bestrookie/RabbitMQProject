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
     * 备份交换机
     */
    public static final String  BACKUP_EXCHANGE_NAME = "backup_exchange";
    /**
     * 备份队列
     */
    public static final String  BACKUP_QUEUE_NAME = "backup_queue";
    /**
     * 警告队列
     */
    public static final String  WARNING_QUEUE_NAME = "warning_queue";

    /**
     * 声明交换机
     * @return 直接型交换机
     */
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }

    /**
     * 声明队列
     * @return 队列
     */
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    /**
     * 绑定
     * @param confirmQueue 队列名称
     * @param confirmChange 交换机名称
     * @return 绑定
     */
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue, @Qualifier("confirmExchange") DirectExchange confirmChange){
        return BindingBuilder.bind(confirmQueue).to(confirmChange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean("backupExchange")
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }
    @Bean("backupQueue")
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }
    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean
    public Binding backupQueueBindingExchange(@Qualifier("backupQueue") Queue backupQueue, @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }
    @Bean
    public Binding warningQueueBindingExchange(@Qualifier("warningQueue") Queue warningQueue, @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }

}
