package com.lik.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup="consumer-group-1", topic="topic-A")
public class TestConsumer1 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("消费者1: " + message);
    }
}
