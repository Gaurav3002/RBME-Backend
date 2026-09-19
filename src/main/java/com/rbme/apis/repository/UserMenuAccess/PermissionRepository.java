package com.rbme.apis.repository.UserMenuAccess;

import com.rbme.apis.entity.UserMenuAccess.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByCode(String code);

    Optional<Permission> findByCode(String code);
}