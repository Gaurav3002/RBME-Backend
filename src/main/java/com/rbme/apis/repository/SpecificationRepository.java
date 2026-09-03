package com.rbme.apis.repository;

import com.rbme.apis.entity.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationRepository extends JpaRepository<ProductSpecification, Long> {
    List<ProductSpecification> findByProductId(Long productId);
    @Modifying
    @Query("""
    DELETE FROM ProductSpecification ps
        WHERE ps.product.id = :productId
    """)
    int deleteByProductId(@Param("productId") Long productId);
}
