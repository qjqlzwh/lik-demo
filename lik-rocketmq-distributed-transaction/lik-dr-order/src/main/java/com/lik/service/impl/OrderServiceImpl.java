package com.lik.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.customer.CustomerAmountLog;
import com.lik.entity.order.Order;
import com.lik.entity.order.OrderItem;
import com.lik.entity.product.Product;
import com.lik.enums.BusinessType;
import com.lik.enums.OrderState;
import com.lik.feign.customer.CustomerFeign;
import com.lik.feign.product.ProductFeign;
import com.lik.feign.product.ProductInventoryFeign;
import com.lik.mapper.OrderMapper;
import com.lik.resp.R;
import com.lik.service.IOrderItemService;
import com.lik.service.IOrderService;
import com.lik.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private ProductInventoryFeign productInventoryFeign;
    @Autowired
    private CustomerFeign customerFeign;
    @Autowired
    private IOrderItemService orderItemService;

    /**
     * 生成预订单
     * @param order
     */
    @Override
    @Transactional
    public void createPreOrder(Order order) {
        Assert.notNull(order, "数据异常！");
        List<OrderItem> orderItems = order.getOrderItems();
        Assert.notEmpty(orderItems, "订单明细不能为空！");
        Assert.notNull(order.getCustomer(), "客户不能为空！");
        order.setState(OrderState.SAVE.getVal());
        order.setSn(IdUtils.snfId());
        this.save(order);

        BigDecimal sumAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            Assert.notNull(orderItem.getProductId(), "产品数据异常！");
            Assert.notNull(orderItem.getPrice(), "产品价格异常！");
            Assert.notNull(orderItem.getQuantity(), "产品下单数量异常！");
            Product product = productFeign.findById(orderItem.getProductId());
            Assert.notNull(product, "产品不存在！");
            orderItem.setProductCode(product.getCode());
            orderItem.setProductName(product.getName());
            orderItem.setOrderId(order.getId());
            orderItem.setRowAmount(orderItem.getPrice().multiply(orderItem.getQuantity()));
            sumAmount = sumAmount.add(orderItem.getRowAmount());
            orderItemService.save(orderItem);
        }

        order.setAmount(sumAmount);
        this.updateById(order);
    }

    /**
     * 减库存
     * @param order
     */
    public void reductionInventory(Order order) {
        R r = productInventoryFeign.subInventoryByOrder(order);
        Assert.isTrue(r.getCode().intValue() == 200, r.getMessage());
    }

    /**
     * 减余额
     * @param order
     */
    public void reductionAmount(Order order) {
        CustomerAmountLog customerAmountLog = new CustomerAmountLog();
        customerAmountLog.setCustomerId(order.getCustomer());
        customerAmountLog.setAmount(order.getAmount().negate());
        customerAmountLog.setTargetType(BusinessType.ORDER.getVal());
        customerAmountLog.setTargetId(order.getId());
        customerAmountLog.setMemo("订单扣减余额");
        R r = customerFeign.updateCustomerAmount(customerAmountLog);
        Assert.isTrue(r.getCode().intValue() == 200, r.getMessage());
    }

    /**
     * 取消订单
     *
     * @param id
     */
    @Override
    public void cancelOrder(Long id) {
        Order order = this.getById(id);
        order.setState(OrderState.CANCEL.getVal());
        updateById(order);
    }
}
