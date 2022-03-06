package com.lik.listener;

import cn.hutool.json.JSONUtil;
import com.lik.constant.MqGroup;
import com.lik.constant.MqTopic;
import com.lik.entity.customer.CustomerAmountLog;
import com.lik.entity.order.Order;
import com.lik.enums.BusinessType;
import com.lik.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 回滚客户余额
 */
@Slf4j
@Component
//@RocketMQMessageListener(consumerGroup = "consumer-customer-group", topic = MqTopic.ORDER_ROLLBACK)
@RocketMQMessageListener(consumerGroup = MqGroup.ORDER_CANCEL, topic = MqTopic.ORDER_ROLLBACK, messageModel = MessageModel.BROADCASTING)
public class OrderRollbackCustomerAmountListener implements RocketMQListener<String> {

    @Autowired
    private ICustomerService customerService;

    @Override
    public void onMessage(String message) {
        log.info("OrderRollbackCustomerAmountListener 回退客户余额start");
        log.info("message: " + message);
        Order order = JSONUtil.toBean(message, Order.class);
        CustomerAmountLog customerAmountLog = new CustomerAmountLog();
        customerAmountLog.setCustomerId(order.getCustomer());
        customerAmountLog.setAmount(order.getAmount());
        customerAmountLog.setTargetType(BusinessType.ORDER.getVal());
        customerAmountLog.setTargetId(order.getId());
        customerAmountLog.setMemo("回滚订单扣减余额");
        customerService.rollbackCustomerAmount(customerAmountLog);
        log.info("OrderRollbackCustomerAmountListener 回退客户余额end");
    }
}
