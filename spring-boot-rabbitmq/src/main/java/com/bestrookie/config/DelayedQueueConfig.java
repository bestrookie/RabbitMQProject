package com.bestrookie.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bestrookie
 * @date 2022/2/8 3:55 下午
 */
@Configuration
public class DelayedQueueConfig {

    // 队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    // routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingKey";

    @Bean
    public CustomExchange delayedExchange(){
         Map<String, Object> arguments = new HashMap<>();
         arguments.put("x-delayed-type","direct");
         return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",true,false,arguments);
    }
    @Bean("delayed.queue")
    public Queue delayedQueue(){
        return QueueBuilder.durable(DELAYED_QUEUE_NAME).build();
    }
    @Bean
    public Binding delayedQueueBinding(@Qualifier("delayed.queue") Queue queue, @Qualifier("delayedExchange") CustomExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
