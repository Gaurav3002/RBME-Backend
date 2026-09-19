package com.rbme.apis.services.UserMenuAccess;

import com.rbme.apis.dto.UserMenuAccess.MenuRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.MenuResponseDTO;
import com.rbme.apis.entity.UserMenuAccess.Menu;
import com.rbme.apis.entity.UserMenuAccess.Permission;
import com.rbme.apis.exception.BadRequestException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.UserMenuAccess.MenuRepository;
import com.rbme.apis.repository.UserMenuAccess.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final PermissionRepository permissionRepository;

    // =========================================================
    // CREATE
    // =========================================================

    @Transactional
    public MenuResponseDTO create(MenuRequestDTO dto) {

        String code = normalizeCode(dto.getCode());

        if (menuRepository.existsByCode(code)) {
            throw new BadRequestException(
                    "Menu already exists"
            );
        }

        Menu menu = new Menu();

        menu.setCode(code);
        menu.setName(dto.getName().trim());
        menu.setIcon(
                dto.getIcon() != null &&
                        !dto.getIcon().isBlank()
                        ? dto.getIcon().trim()
                        : null
        );
        menu.setRoute(
                dto.getRoute() != null &&
                        !dto.getRoute().isBlank()
                        ? dto.getRoute().trim()
                        : null
        );
        menu.setDisplayOrder(
                dto.getDisplayOrder() != null
                        ? dto.getDisplayOrder()
                        : 0
        );
        menu.setActive(
                dto.getActive() != null
                        ? dto.getActive()
                        : true
        );

        // -----------------------------------------------------
        // Parent
        // -----------------------------------------------------

        if (dto.getParentId() != null) {

            Menu parent =
                    menuRepository
                            .findById(dto.getParentId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Parent menu not found"
                                    )
                            );

            if (!Boolean.TRUE.equals(parent.getActive())) {
                throw new BadRequestException(
                        "Parent menu is inactive"
                );
            }

            menu.setParent(parent);
        }

        // -----------------------------------------------------
        // Permission
        // -----------------------------------------------------

        if (dto.getPermissionId() != null) {

            Permission permission =
                    permissionRepository
                            .findById(dto.getPermissionId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Permission not found"
                                    )
                            );

            if (!Boolean.TRUE.equals(permission.getActive())) {
                throw new BadRequestException(
                        "Permission is inactive"
                );
            }

            menu.setPermission(permission);
        }

        menuRepository.save(menu);

        return convertToDTO(menu);
    }

    // =========================================================
    // ACCESSIBLE MENU TREE
    // =========================================================

    @Transactional(readOnly = true)
    public List<MenuResponseDTO> getRootMenus() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        Set<String> userPermissions =
                getUserPermissions(authentication);

        List<Menu> rootMenus =
                menuRepository
                        .findByParentIsNullOrderByDisplayOrderAsc();

        return rootMenus
                .stream()
                .filter(menu ->
                        Boolean.TRUE.equals(
                                menu.getActive()
                        )
                )
                .filter(menu ->
                        hasAccessOrChildAccess(
                                menu,
                                userPermissions
                        )
                )
                .map(menu ->
                        convertToDTO(
                                menu,
                                userPermissions
                        )
                )
                .toList();
    }

    // =========================================================
    // USER PERMISSIONS
    // =========================================================

    private Set<String> getUserPermissions(
            Authentication authentication
    ) {

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            return Collections.emptySet();
        }

        return authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(permission ->
                        permission != null &&
                                !permission.isBlank()
                )
                .collect(Collectors.toSet());
    }

    // =========================================================
    // ACCESS CHECK
    // =========================================================

    private boolean hasAccessOrChildAccess(
            Menu menu,
            Set<String> userPermissions
    ) {

        if (!Boolean.TRUE.equals(menu.getActive())) {
            return false;
        }

        // -----------------------------------------------------
        // Menu has permission
        // -----------------------------------------------------

        if (menu.getPermission() != null) {

            String permissionCode =
                    menu.getPermission().getCode();

            if (permissionCode != null &&
                    userPermissions.contains(
                            permissionCode
                    )) {

                return true;
            }
        }

        // -----------------------------------------------------
        // Group menu
        // -----------------------------------------------------

        if (menu.getChildren() != null &&
                !menu.getChildren().isEmpty()) {

            return menu.getChildren()
                    .stream()
                    .anyMatch(child ->
                            hasAccessOrChildAccess(
                                    child,
                                    userPermissions
                            )
                    );
        }

        return false;
    }

    // =========================================================
    // FILTERED DTO
    // =========================================================

    private MenuResponseDTO convertToDTO(
            Menu menu,
            Set<String> userPermissions
    ) {

        MenuResponseDTO dto =
                new MenuResponseDTO();

        dto.setId(menu.getId());
        dto.setCode(menu.getCode());
        dto.setName(menu.getName());
        dto.setIcon(menu.getIcon());
        dto.setRoute(menu.getRoute());
        dto.setDisplayOrder(menu.getDisplayOrder());
        dto.setActive(menu.getActive());

        if (menu.getParent() != null) {
            dto.setParentId(
                    menu.getParent().getId()
            );
        }

        if (menu.getPermission() != null) {
            dto.setPermissionId(
                    menu.getPermission().getId()
            );
        }

        if (menu.getChildren() != null) {

            dto.setChildren(
                    menu.getChildren()
                            .stream()
                            .filter(child ->
                                    Boolean.TRUE.equals(
                                            child.getActive()
                                    )
                            )
                            .filter(child ->
                                    hasAccessOrChildAccess(
                                            child,
                                            userPermissions
                                    )
                            )
                            .map(child ->
                                    convertToDTO(
                                            child,
                                            userPermissions
                                    )
                            )
                            .toList()
            );
        }

        return dto;
    }

    // =========================================================
    // GET ALL - ADMIN MANAGEMENT
    // =========================================================

    @Transactional(readOnly = true)
    public List<MenuResponseDTO> getAll() {

        return menuRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Transactional
    public void delete(Long id) {

        Menu menu =
                menuRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Menu not found"
                                )
                        );

        /*
         * Soft delete is safer because this menu can be
         * referenced by other configuration.
         */
        menu.setActive(false);

        menuRepository.save(menu);
    }

    // =========================================================
    // NORMAL DTO
    // =========================================================

    private MenuResponseDTO convertToDTO(Menu menu) {

        MenuResponseDTO dto =
                new MenuResponseDTO();

        dto.setId(menu.getId());
        dto.setCode(menu.getCode());
        dto.setName(menu.getName());
        dto.setIcon(menu.getIcon());
        dto.setRoute(menu.getRoute());
        dto.setDisplayOrder(menu.getDisplayOrder());
        dto.setActive(menu.getActive());

        if (menu.getParent() != null) {
            dto.setParentId(
                    menu.getParent().getId()
            );
        }

        if (menu.getPermission() != null) {
            dto.setPermissionId(
                    menu.getPermission().getId()
            );
        }

        if (menu.getChildren() != null) {

            dto.setChildren(
                    menu.getChildren()
                            .stream()
                            .map(this::convertToDTO)
                            .toList()
            );
        }

        return dto;
    }

    // =========================================================
    // NORMALIZE CODE
    // =========================================================

    private String normalizeCode(String code) {

        if (code == null || code.isBlank()) {
            throw new BadRequestException(
                    "Menu code is required"
            );
        }

        return code
                .trim()
                .toUpperCase()
                .replaceAll("[^A-Z0-9_]", "_")
                .replaceAll("_+", "_");
    }
}