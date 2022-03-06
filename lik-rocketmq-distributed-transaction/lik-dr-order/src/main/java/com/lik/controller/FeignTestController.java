package com.lik.controller;

import com.lik.dto.product.ProductDTO;
import com.lik.entity.product.Product;
import com.lik.feign.product.ProductFeign;
import com.lik.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

public class FeignTestController {

    @Autowired
    private ProductFeign productFeign;

    @GetMapping("/findProductById/{id}")
    private Product findById(@PathVariable("id") Long id) {
        return productFeign.findById(id);
    }

    @GetMapping("/findProduct")
    public R findProduct(String name, String code) {
        return productFeign.findProduct(name, code);
    }

    @GetMapping("/findProduct2")
    public R findProduct2(ProductDTO productDTO) {
        return productFeign.findProduct2(productDTO);
    }

    @GetMapping("/findProduct3")
    public R findProduct3() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "张三");
        hashMap.put("age", 22);
        return productFeign.findProduct3(hashMap);
    }

    @PostMapping("/findProduct4")
    public R findProduct4() {
        System.out.println("11111111111111111111111");
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("路飞");
        productDTO.setCode("123");
        return productFeign.findProduct4(productDTO);
    }
}
