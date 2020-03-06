package com.jcso.springboot.api.commons.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private Double prize;

    @Transient
    private Integer port;

    //private static final long serialVersionUID = -6434651042249034991L;

}
