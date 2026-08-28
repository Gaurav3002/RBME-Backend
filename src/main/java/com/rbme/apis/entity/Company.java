package com.rbme.apis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    // company/logo/logo.png
    private String logo;

    // company/banner/banner.jpg
    private String banner;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;

    private Boolean active = true;

    @OneToMany(mappedBy = "company")
    private List<ProductCategory> categories;

    @OneToMany(mappedBy = "company")
    private List<Product> products;

}