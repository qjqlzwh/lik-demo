package com.lik.controller;


import cn.hutool.json.JSONUtil;
import com.lik.dto.product.ProductDTO;
import com.lik.entity.product.Product;
import com.lik.resp.R;
import com.lik.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 产品 前端控制器
 * </p>
 *
 * @author lik
 * @since 2022-02-04
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/findById/{id}")
    public Product findById(@PathVariable("id") Long id) {
        Product product = productService.getById(id);
        return product;
    }

    @GetMapping("/findProduct")
    public R findProduct(@RequestParam("name") String name1, String code) {
        String msg = "name1=" + name1 + " code=" + code;
        log.info(msg);
        return R.ok().data(msg);
    }

    @GetMapping("/findProduct2")
    public R findProduct2(ProductDTO productDTO) {
        log.info(productDTO.toString());
        return R.ok().data(productDTO.toString());
    }

    @GetMapping("/findProduct3")
    public R findProduct3(@RequestParam Map<String, Object> map) {
        String msg = JSONUtil.toJsonStr(map);
        log.info(msg);
        return R.ok().data(map);
    }

    @PostMapping("/findProduct4")
    public R findProduct4(@RequestBody ProductDTO productDTO) {
        log.info(productDTO.toString());
        return R.ok().data(productDTO.toString());
    }
}
