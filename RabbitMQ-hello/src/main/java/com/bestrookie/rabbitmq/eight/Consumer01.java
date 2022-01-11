package com.bestrookie.rabbitmq.eight;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/9 22:55
 */
public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAL_EXCHANGE = "deal_exchange";
    public static final String DEAL_QUEUE = "queue_exchange";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAL_EXCHANGE,BuiltinExchangeType.DIRECT);
        //声明队列
        Map<String, Object> arguments  = new HashMap<>();
        //设置过期时间
        //arguments.put("x-message-ttl",10000);
        //正常队列设置死信队列
        arguments.put("x-dead-letter-exchange",DEAL_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","rookie");
        //设置最大长度
//        arguments.put("x-max-length",6);
        //声明正常的队列
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        //声明死信队列
        channel.queueDeclare(DEAL_QUEUE,false,false,false,null);
        //绑定普通的交换机与队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"bestrookie");
        channel.queueBind(DEAL_QUEUE,DEAL_EXCHANGE,"rookie");
        System.out.println("等待接收消息.....");
        DeliverCallback deliverCallback = (tag,msg)->{
            String message = new String(msg.getBody(),StandardCharsets.UTF_8);
            if (message.equals("info5")){
                channel.basicReject(msg.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("01接收消息：" + message);
                channel.basicAck(msg.getEnvelope().getDeliveryTag(),false);
            }
        };
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,cancel->{});
    }
}
