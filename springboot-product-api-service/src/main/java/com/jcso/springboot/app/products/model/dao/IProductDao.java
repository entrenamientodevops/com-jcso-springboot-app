package com.jcso.springboot.app.products.model.dao;

import com.jcso.springboot.api.commons.model.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDao extends CrudRepository<Product,  Long> {
}
