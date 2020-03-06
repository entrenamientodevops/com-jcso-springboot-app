package com.jcso.springboot.app.item.model.entity;

import com.jcso.springboot.api.commons.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Product product;
    private Integer amount;

    public Double getQuantity(){
        return product.getPrize() * amount.doubleValue();
    }

}
