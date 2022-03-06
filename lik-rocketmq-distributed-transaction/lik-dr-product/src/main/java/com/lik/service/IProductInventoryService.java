package com.lik.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lik.entity.order.Order;
import com.lik.entity.product.ProductInventory;

import java.math.BigDecimal;

/**
 * <p>
 * 产品库存 服务类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
public interface IProductInventoryService extends IService<ProductInventory> {

    Integer subInventory(Long productId, BigDecimal qty);

    void subInventory(Order order);

    void addInventory(Order order);
}
