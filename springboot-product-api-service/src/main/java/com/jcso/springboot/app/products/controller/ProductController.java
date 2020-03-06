package com.jcso.springboot.app.products.controller;

import com.jcso.springboot.api.commons.model.entity.Product;
import com.jcso.springboot.app.products.model.Service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/product")
public class ProductController {

    @Autowired
    private Environment environment;

    @Autowired
    private IProductService productService;

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/list")
    public List<Product> getProducts(){
        return productService.findAll().stream().map(product -> {
            //product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            product.setPort(port);
            return product;
        }).collect(Collectors.toList());
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id) throws Exception {
        Product product = productService.findById(id);
        //product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        product.setPort(port);

        //sleep(2000L);

        return product;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody  Product product){
        return productService.save(product);
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product edit(@RequestBody Product product, @PathVariable Long id) throws Exception {
        return productService.edit(product, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        productService.deleteById(id);
    }


}
