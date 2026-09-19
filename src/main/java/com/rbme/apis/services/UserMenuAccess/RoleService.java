package com.rbme.apis.services.UserMenuAccess;

import com.rbme.apis.dto.UserMenuAccess.RoleRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.RoleResponseDTO;
import com.rbme.apis.entity.UserMenuAccess.Permission;
import com.rbme.apis.entity.UserMenuAccess.Role;
import com.rbme.apis.exception.BadRequestException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.UserMenuAccess.PermissionRepository;
import com.rbme.apis.repository.UserMenuAccess.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    // =========================================================
    // CREATE ROLE
    // =========================================================

    @Transactional
    public RoleResponseDTO create(RoleRequestDTO dto) {

        if (dto.getName() == null ||
                dto.getName().isBlank()) {

            throw new BadRequestException(
                    "Role name is required"
            );
        }

        String roleName = dto.getName().trim();

        if (roleRepository.existsByName(roleName)) {
            throw new BadRequestException(
                    "Role already exists"
            );
        }

        Role role = new Role();

        role.setName(roleName);
        role.setDescription(dto.getDescription());
        role.setActive(
                dto.getActive() != null
                        ? dto.getActive()
                        : true
        );

        role.setPermissions(
                getPermissions(dto.getPermissionIds())
        );

        roleRepository.save(role);

        return convertToDTO(role);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAll() {

        return roleRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Transactional(readOnly = true)
    public RoleResponseDTO getById(Long id) {

        Role role =
                roleRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found"
                                )
                        );

        return convertToDTO(role);
    }

    // =========================================================
    // UPDATE ROLE
    // =========================================================

    @Transactional
    public RoleResponseDTO update(
            Long id,
            RoleRequestDTO dto
    ) {

        Role role =
                roleRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found"
                                )
                        );

        if (dto.getName() == null ||
                dto.getName().isBlank()) {

            throw new BadRequestException(
                    "Role name is required"
            );
        }

        String roleName = dto.getName().trim();

        if (!role.getName().equalsIgnoreCase(roleName)
                && roleRepository.existsByName(roleName)) {

            throw new BadRequestException(
                    "Role already exists"
            );
        }

        role.setName(roleName);
        role.setDescription(dto.getDescription());
        role.setActive(
                dto.getActive() != null
                        ? dto.getActive()
                        : true
        );

        /*
         * Replace complete permission list.
         *
         * Example:
         *
         * Old:
         * PRODUCT_VIEW
         * COMPANY_VIEW
         *
         * New:
         * PRODUCT_VIEW
         * DASHBOARD_VIEW
         *
         * COMPANY_VIEW will be removed automatically.
         */
        role.setPermissions(
                getPermissions(dto.getPermissionIds())
        );

        roleRepository.save(role);

        return convertToDTO(role);
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Transactional
    public void delete(Long id) {

        Role role =
                roleRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found"
                                )
                        );

        /*
         * Recommended soft delete.
         *
         * Don't physically delete a role that may already
         * be assigned to users.
         */
        role.setActive(false);

        roleRepository.save(role);
    }

    // =========================================================
    // ASSIGN SINGLE PERMISSION
    // =========================================================

    @Transactional
    public RoleResponseDTO assignPermission(
            Long roleId,
            Long permissionId
    ) {

        Role role =
                roleRepository
                        .findById(roleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found"
                                )
                        );

        Permission permission =
                permissionRepository
                        .findById(permissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Permission not found"
                                )
                        );

        role.getPermissions().add(permission);

        roleRepository.save(role);

        return convertToDTO(role);
    }

    // =========================================================
    // REMOVE SINGLE PERMISSION
    // =========================================================

    @Transactional
    public RoleResponseDTO removePermission(
            Long roleId,
            Long permissionId
    ) {

        Role role =
                roleRepository
                        .findById(roleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found"
                                )
                        );

        role.getPermissions()
                .removeIf(permission ->
                        permission.getId().equals(permissionId)
                );

        roleRepository.save(role);

        return convertToDTO(role);
    }

    // =========================================================
    // GET PERMISSIONS
    // =========================================================

    private Set<Permission> getPermissions(
            Set<Long> permissionIds
    ) {

        if (permissionIds == null ||
                permissionIds.isEmpty()) {

            return new HashSet<>();
        }

        List<Permission> permissions =
                permissionRepository
                        .findAllById(permissionIds);

        if (permissions.size() != permissionIds.size()) {

            throw new ResourceNotFoundException(
                    "One or more permissions not found"
            );
        }

        return new HashSet<>(permissions);
    }

    // =========================================================
    // DTO
    // =========================================================

    private RoleResponseDTO convertToDTO(Role role) {

        RoleResponseDTO dto =
                new RoleResponseDTO();

        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setActive(role.getActive());

        dto.setPermissions(
                role.getPermissions()
                        .stream()
                        .map(Permission::getCode)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}