package com.lik.controller;


import cn.hutool.json.JSONUtil;
import com.lik.entity.order.Order;
import com.lik.resp.R;
import com.lik.service.IProductInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 产品库存 前端控制器
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Slf4j
@RestController
@RequestMapping("/product-inventory")
public class ProductInventoryController {

    @Autowired
    private IProductInventoryService productInventoryService;

    /**
     * 扣减库存
     * @param productId
     * @param qty
     * @return
     */
    @PostMapping("/subInventory")
    public Integer subInventory(Long productId, BigDecimal qty) {
        return productInventoryService.subInventory(productId, qty);
    }

    /**
     * 根据订单扣减库存
     * @param order
     * @return
     */
    @PostMapping("/subInventoryByOrder")
    public R subInventoryByOrder(@RequestBody Order order) {
        log.info(JSONUtil.toJsonStr(order));
        productInventoryService.subInventory(order);
        return R.ok();
    }
}
