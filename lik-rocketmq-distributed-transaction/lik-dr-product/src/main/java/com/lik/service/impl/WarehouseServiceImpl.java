package com.lik.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.product.Warehouse;
import com.lik.mapper.WarehouseMapper;
import com.lik.service.IWarehouseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库 服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

}
