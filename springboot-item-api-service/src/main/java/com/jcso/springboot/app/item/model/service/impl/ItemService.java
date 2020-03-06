package com.jcso.springboot.app.item.model.service.impl;

import com.jcso.springboot.app.item.model.entity.Item;
import com.jcso.springboot.api.commons.model.entity.Product;
import com.jcso.springboot.app.item.model.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("restService")
public class ItemService implements IItemService {

    @Autowired
    private RestTemplate itemRest;

    @Override
    public List<Item> findAll() {

        List<Product> products = Arrays.asList(itemRest.getForObject("http://product-service/list", Product[].class));
        return products.stream().map(product -> new Item(product, 1)).collect(Collectors.toList());

    }

    @Override
    public Item findById(Long id, Integer quantity) {

        Map<String, String> pathVariable = new HashMap<String, String>();
        pathVariable.put("id", id.toString());

        Product product = itemRest.getForObject("http://product-service/product/{id}", Product.class, pathVariable);

        return new Item(product, quantity);
    }

    @Override
    public Product save(Product product) {
        HttpEntity<Product> httpEntity = new HttpEntity<Product>(product);

        ResponseEntity<Product> responseEntity = itemRest.exchange("http://product-service/create", HttpMethod.POST, httpEntity, Product.class);

        return responseEntity.getBody();
    }

    @Override
    public Product edit(Product product, Long id) {

        Map<String, String> pathVariable = new HashMap<String, String>();
        pathVariable.put("id", id.toString());

        HttpEntity<Product> httpEntity = new HttpEntity<Product>(product);

        ResponseEntity<Product> responseEntity = itemRest.exchange("http://product-service/edit/{id}",
                HttpMethod.PUT, httpEntity, Product.class, pathVariable);

        return responseEntity.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String, String> pathVariable = new HashMap<String, String>();
        pathVariable.put("id", id.toString());

        itemRest.delete("http://product-service/delete/{id}", pathVariable);
    }
}
