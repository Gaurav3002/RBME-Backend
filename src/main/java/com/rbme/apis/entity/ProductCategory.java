package com.rbme.apis.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "product_categories")
@Data
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    private Boolean active = true;

    @OneToMany(mappedBy = "category")
    private List<ProductType> types;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}