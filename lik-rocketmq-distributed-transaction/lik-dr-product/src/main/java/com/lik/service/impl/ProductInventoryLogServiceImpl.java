package com.lik.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.product.ProductInventoryLog;
import com.lik.mapper.ProductInventoryLogMapper;
import com.lik.service.IProductInventoryLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存日志 服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-13
 */
@Service
public class ProductInventoryLogServiceImpl extends ServiceImpl<ProductInventoryLogMapper, ProductInventoryLog> implements IProductInventoryLogService {

}
