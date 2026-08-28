package com.rbme.apis.repository;

import com.rbme.apis.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndCategoryId(String name, Long categoryId);

    List<ProductType> findByCategoryId(Long categoryId);
}
