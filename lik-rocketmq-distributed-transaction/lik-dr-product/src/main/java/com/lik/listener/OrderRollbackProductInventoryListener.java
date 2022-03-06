package com.lik.listener;

import cn.hutool.json.JSONUtil;
import com.lik.constant.MqGroup;
import com.lik.constant.MqTopic;
import com.lik.entity.order.Order;
import com.lik.service.IProductInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 回滚产品库存
 */
@Slf4j
@Component
//@RocketMQMessageListener(consumerGroup = "consumer-product-group", topic = MqTopic.ORDER_ROLLBACK)
@RocketMQMessageListener(consumerGroup = MqGroup.ORDER_CANCEL, topic = MqTopic.ORDER_ROLLBACK, messageModel = MessageModel.BROADCASTING)
public class OrderRollbackProductInventoryListener implements RocketMQListener<String> {

    @Autowired
    private IProductInventoryService productInventoryService;

    @Override
    public void onMessage(String message) {
        log.info("OrderRollbackProductInventoryListener 回退订单产品库存start");
        log.info("message: " + message);
        productInventoryService.addInventory(JSONUtil.toBean(message, Order.class));
        log.info("OrderRollbackProductInventoryListener 回退订单产品库存end");
    }
}
