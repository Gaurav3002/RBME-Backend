package com.rbme.apis.repository;

import com.rbme.apis.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    boolean existsByNameAndCompanyId(String name, Long companyId);

    List<ProductCategory> findByCompanyId(Long companyId);
}
