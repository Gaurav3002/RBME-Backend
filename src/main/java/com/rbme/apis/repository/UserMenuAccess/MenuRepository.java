package com.rbme.apis.repository.UserMenuAccess;

import com.rbme.apis.entity.UserMenuAccess.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByCode(String code);

    Optional<Menu> findByCode(String code);

    List<Menu> findByParentIsNullOrderByDisplayOrderAsc();
}