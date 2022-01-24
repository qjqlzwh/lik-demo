package com.lik.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 出现多个相同的 consumerGroup 时，启动报错，
 */
@Slf4j
@Component
//@RocketMQMessageListener(consumerGroup="consumer-group-1", topic="topic-B")
public class TestConsumer4 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("消费者3: " + message);
    }
}
