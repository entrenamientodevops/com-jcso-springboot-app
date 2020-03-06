package com.jcso.springboot.app.item.model.service.impl;

import com.jcso.springboot.api.commons.model.entity.Product;
import com.jcso.springboot.app.item.client.ProductRestClient;
import com.jcso.springboot.app.item.model.entity.Item;
import com.jcso.springboot.app.item.model.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
public class ItemServiceFeign implements IItemService {

    @Autowired
    private ProductRestClient restClient;

    @Override
    public List<Item> findAll() {
        return restClient.list().stream().map(product -> new Item(product, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        return new Item(restClient.getProduct(id), quantity);
    }

    @Override
    public Product save(Product product) {
        return restClient.create(product);
    }

    @Override
    public Product edit(Product product, Long id) {
        return restClient.edit(product, id);
    }

    @Override
    public void delete(Long id) {
        restClient.delete(id);
    }
}
