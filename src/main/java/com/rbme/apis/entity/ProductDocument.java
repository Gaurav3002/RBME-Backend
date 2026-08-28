package com.rbme.apis.entity;

import jakarta.persistence.*;

@Entity
@Table(name="product_documents")
public class ProductDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String fileUrl;

    private String documentType;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}