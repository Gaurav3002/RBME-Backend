package com.rbme.apis.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "product_types")
@Data
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean active;
    @ManyToOne
    @JoinColumn(name="category_id")
    private ProductCategory category;

    @OneToMany(mappedBy = "productType")
    private List<Product> products;
}