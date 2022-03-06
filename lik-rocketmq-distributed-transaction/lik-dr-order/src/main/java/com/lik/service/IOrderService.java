package com.lik.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lik.entity.order.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
public interface IOrderService extends IService<Order> {

    /**
     * 生成预订单
     * @param order
     */
    void createPreOrder(Order order);

    void reductionInventory(Order order);

    void reductionAmount(Order order);

    /**
     * 取消订单
     * @param id
     */
    void cancelOrder(Long id);
}
