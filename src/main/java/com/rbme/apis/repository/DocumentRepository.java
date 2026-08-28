package com.rbme.apis.repository;

import com.rbme.apis.entity.ProductDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<ProductDocument, Long> {
}
