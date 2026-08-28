package com.rbme.apis.repository;

import com.rbme.apis.entity.Company;
import com.rbme.apis.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByModelNoIgnoreCase(String modelNo);
    boolean existsByModelNoIgnoreCaseAndIdNot(String modelNo, Long id);
    List<Product> findByCompanyId(Long companyId);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByProductTypeId(Long typeId);
    List<Product> findByActive(Boolean active);
    List<Product> findByFeatured(Boolean featured);
}
