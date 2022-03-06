package com.lik.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@RocketMQMessageListener(consumerGroup = "consumer-group-5", topic = "topic-D")
public class TestConsumer9 implements RocketMQReplyListener<String, String> {

    @Override
    public String onMessage(String message) {
        log.info("消费者9: " + message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("消费者9已消费");
        return "消费者9已消费 " + message;
    }
}
