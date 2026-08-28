package com.rbme.apis.repository;

import com.rbme.apis.entity.ProductFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<ProductFeature, Long> {
}
