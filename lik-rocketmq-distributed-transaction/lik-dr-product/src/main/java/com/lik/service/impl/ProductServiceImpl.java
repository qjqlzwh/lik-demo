package com.lik.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lik.entity.product.Product;
import com.lik.mapper.ProductMapper;
import com.lik.service.IProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品 服务实现类
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
