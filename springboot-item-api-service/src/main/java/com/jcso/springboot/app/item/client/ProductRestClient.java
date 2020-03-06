package com.jcso.springboot.app.item.client;

import com.jcso.springboot.api.commons.model.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductRestClient {

    @GetMapping("/list")
    List<Product> list();

    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable Long id);

    @PostMapping("/create")
    Product create(@RequestBody Product product);

    @PutMapping("/edit/{id}")
    Product edit(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id);
}
