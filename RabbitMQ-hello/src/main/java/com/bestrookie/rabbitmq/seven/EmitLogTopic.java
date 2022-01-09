package com.bestrookie.rabbitmq.seven;

import com.bestrookie.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bestrookie
 * @version 1.0
 * @date 2022/1/9 17:46
 */
public class EmitLogTopic {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        Map<String, String> bindMap = new HashMap<>();
        bindMap.put("quick.orange.rabbit","被队列Q1 Q2接收到");
        bindMap.put("lazy.orange.eephant","被Q1 Q2接受");
        bindMap.put("wqe.orange.asd","被Q1接受");
        bindMap.put("lazy.asd","被Q2接受");
        bindMap.put("asd.asd.rabbit","被Q2接受");
        for (Map.Entry<String, String> mapEntry : bindMap.entrySet()){
            String key = mapEntry.getKey();
            String msg = mapEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME,key,null,msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发送消息");
        }

    }
}
