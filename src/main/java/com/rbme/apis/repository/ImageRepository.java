package com.rbme.apis.repository;

import com.rbme.apis.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductIdOrderBySortOrderAsc(Long productId);

    void deleteByProductId(Long productId);

    @Modifying
    @Query("""
        DELETE FROM ProductImage pi
        WHERE pi.id = :imageId
        AND pi.product.id = :productId
    """)
    int deleteByIdAndProductId(
            @Param("imageId") Long imageId,
            @Param("productId") Long productId
    );
}