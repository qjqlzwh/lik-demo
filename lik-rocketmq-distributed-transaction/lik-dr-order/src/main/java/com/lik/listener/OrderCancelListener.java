package com.lik.listener;

import cn.hutool.json.JSONUtil;
import com.lik.constant.MqGroup;
import com.lik.constant.MqTopic;
import com.lik.entity.order.Order;
import com.lik.exception.ExceptionUtils;
import com.lik.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqGroup.ORDER_CANCEL, topic = MqTopic.ORDER_ROLLBACK, messageModel = MessageModel.BROADCASTING)
public class OrderCancelListener implements RocketMQListener<String> {

    @Autowired
    private IOrderService orderService;

    @Override
    public void onMessage(String message) {
        log.info("OrderCancelListener 取消订单 start");
        Order order = JSONUtil.toBean(message, Order.class);
        orderService.cancelOrder(order.getId());
        log.info("OrderCancelListener 取消订单 end");
    }
}
