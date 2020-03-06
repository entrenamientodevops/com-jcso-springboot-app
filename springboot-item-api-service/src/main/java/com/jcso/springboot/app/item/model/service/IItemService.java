package com.jcso.springboot.app.item.model.service;

import com.jcso.springboot.app.item.model.entity.Item;
import com.jcso.springboot.api.commons.model.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IItemService {

    List<Item> findAll();
    Item findById(Long id, Integer quantity);

    Product save(Product product);
    Product edit(Product product, Long id);
    void delete(Long id);

}
