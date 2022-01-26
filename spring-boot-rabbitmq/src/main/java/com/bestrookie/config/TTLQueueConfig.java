package com.bestrookie.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

/**
 * @author bestrookie
 * @date 2022/1/26 10:46 上午
 */
@Configuration
public class TTLQueueConfig {
    //普通交换机
    public static final String NORMAL_EXCHANGE = "normal";
    //死信交换机
    public static final String DEAD_EXCHANGE = "dead";
    //普通队列
    public static final String NORMAL_QUEUE_A = "normal_queueA";
    public static final String NORMAL_QUEUE_B = "normal_queueB";
    //死信队列
    public static final String DEAD_QUEUE = "dead_queue";
    @Bean("normalExchange")
    public DirectExchange normalExchange(){
        return new DirectExchange(NORMAL_EXCHANGE);
    }
    @Bean("deadExchange")
    public DirectExchange deadExchange(){
        return new DirectExchange(DEAD_EXCHANGE);
    }
    @Bean("normalQueueA")
    public Queue normalQueueA(){
        Map<String, Object> arguments = new HashMap<>();
        //绑定死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信
        arguments.put("x-dead-letter-routing-key","YD");
        //设置过期时间
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(NORMAL_QUEUE_A).withArguments(arguments).build();
    }
    @Bean("normalQueueB")
    public Queue normalQueueB(){
        Map<String, Object> arguments = new HashMap<>();
        //绑定死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信
        arguments.put("x-dead-letter-routing-key","YD");
        //设置过期时间
        arguments.put("x-message-ttl",40000);
        return QueueBuilder.durable(NORMAL_QUEUE_B).withArguments(arguments).build();
    }
    @Bean("deadQueue")
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }


    @Bean
    public Binding  queueABanding(@Qualifier("normalQueueA")Queue queue, @Qualifier("normalExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("XA");
    }
    @Bean
    public Binding  queueBBanding(@Qualifier("normalQueueB")Queue queue, @Qualifier("normalExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("XB");
    }
    @Bean
    public Binding  queueDeadBanding(@Qualifier("deadQueue")Queue queue, @Qualifier("deadExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("YD");
    }
}
