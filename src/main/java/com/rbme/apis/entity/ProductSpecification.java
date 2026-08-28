package com.rbme.apis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="product_specifications")
@Data
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specificationName;

    private String specificationValue;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}