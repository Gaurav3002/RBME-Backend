package com.rbme.apis.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelNo;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnail;

    private String youtubeUrl;

    private boolean featured;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("sortOrder ASC")
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductSpecification> specifications;

    @OneToMany(mappedBy = "product")
    private List<ProductFeature> features;

    @OneToMany(mappedBy = "product")
    private List<ProductDocument> documents;
}