package com.lik.feign.product;

import com.lik.entity.order.Order;
import com.lik.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("lik-dr-product")
public interface ProductInventoryFeign {

    /**
     * 扣减库存
     * @param productId
     * @param qty
     * @return
     */
    @PostMapping("/product-inventory/subInventory")
    Integer subInventory(@RequestParam("productId") Long productId, @RequestParam("qty") BigDecimal qty);

    @PostMapping("/product-inventory/subInventoryByOrder")
    R subInventoryByOrder(@RequestBody Order order);

}
