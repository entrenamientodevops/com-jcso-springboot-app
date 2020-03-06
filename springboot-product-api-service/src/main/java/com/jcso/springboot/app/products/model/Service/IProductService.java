package com.jcso.springboot.app.products.model.Service;

//import com.jcso.springboot.app.products.model.entity.Product;
import com.jcso.springboot.api.commons.model.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAll();

    Product findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

    Product edit(Product product, Long id) throws Exception;
}
