package com.lik.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lik.entity.product.ProductInventory;

import java.math.BigDecimal;

/**
 * <p>
 * 产品库存 Mapper 接口
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
public interface ProductInventoryMapper extends BaseMapper<ProductInventory> {

    /**
     * 更新产品库存
     * @param productId
     * @param qty
     * @return
     */
    int updateInventory(Long productId, BigDecimal qty);
}
