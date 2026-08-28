package com.rbme.apis.repository;

import com.rbme.apis.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByName(String name);
    List<Company> findByActiveTrue();
    Optional<Company> findByIdAndActiveTrue(Long id);

}
