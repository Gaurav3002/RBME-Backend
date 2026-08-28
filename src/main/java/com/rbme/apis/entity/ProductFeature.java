package com.rbme.apis.entity;

import jakarta.persistence.*;

@Entity
@Table(name="product_features")
public class ProductFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String feature;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}