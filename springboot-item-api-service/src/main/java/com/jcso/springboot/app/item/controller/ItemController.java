package com.jcso.springboot.app.item.controller;

import com.jcso.springboot.app.item.model.entity.Item;
import com.jcso.springboot.api.commons.model.entity.Product;
import com.jcso.springboot.app.item.model.service.IItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class ItemController {

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("serviceFeign")
    private IItemService itemService;

    @Value("${config-text}")
    private String text;

    @Value("${server.port}")
    private String port;


    @GetMapping("/list")
    public List<Item> list(){
        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/item/{id}/cantidad/{quantity}")
    public Item listDetail(@PathVariable  Long id, @PathVariable Integer quantity){
        return itemService.findById(id, quantity);

    }

    @GetMapping("/config")
    public ResponseEntity<?> getConfig(){
        Map<String, String> map = new HashMap<String, String>();

        map.put("Texto", text);
        map.put("Puerto", port);


        if(environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equals("dev")){
            map.put("nombre", environment.getProperty("config.author.name"));
            map.put("email", environment.getProperty("config.author.email"));
        }

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product){
        return itemService.save(product);
    }


    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product edit(@RequestBody Product product, @PathVariable Long id){
        return itemService.edit(product, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        itemService.delete(id);
    }




    public Item alternativeMethod(Long id, Integer quantity){
        Item item = new Item();
        Product product = new Product();

        item.setAmount(quantity);
        product.setId(id);
        product.setPrize(564875.00);
        product.setName("Camara Deportiva");

        item.setProduct(product);
        return item;

    }
}
