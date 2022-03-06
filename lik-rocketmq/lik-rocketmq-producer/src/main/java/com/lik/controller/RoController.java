package com.lik.controller;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ro")
public class RoController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("test1")
    public String test1() {
        String msg = "消息 " + IdUtil.getSnowflake(1, 1).nextId();
        log.info(msg);
        Message<String> message = MessageBuilder.withPayload(msg).build();

        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("topic-F", message, null);
        log.info("111111111111111111111111111111111");
        return "success";
    }
}
