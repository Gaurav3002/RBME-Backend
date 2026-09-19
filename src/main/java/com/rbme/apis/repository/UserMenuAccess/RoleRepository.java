package com.rbme.apis.repository.UserMenuAccess;

import com.rbme.apis.entity.UserMenuAccess.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}