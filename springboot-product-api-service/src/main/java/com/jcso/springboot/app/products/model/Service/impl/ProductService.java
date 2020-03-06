package com.jcso.springboot.app.products.model.Service.impl;

import com.jcso.springboot.app.products.model.Service.IProductService;
import com.jcso.springboot.app.products.model.dao.IProductDao;
import com.jcso.springboot.api.commons.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    @Transactional
    public Product edit(Product product, Long id) throws Exception {
        Product getProduct = productDao.findById(id).orElse(null);

        if (getProduct == null){
                throw new Exception("El ID " + id.toString() + " no existe.");
        }

        getProduct.setName(product.getName());
        getProduct.setPrize(product.getPrize());

        return productDao.save(getProduct);


    }

    @Override
    @Transactional
    public void deleteById(@PathVariable Long id) {
        productDao.deleteById(id);
    }
}
