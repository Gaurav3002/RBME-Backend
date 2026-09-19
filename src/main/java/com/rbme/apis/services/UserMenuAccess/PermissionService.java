package com.rbme.apis.services.UserMenuAccess;

import com.rbme.apis.dto.UserMenuAccess.PermissionRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.PermissionResponseDTO;
import com.rbme.apis.entity.UserMenuAccess.Permission;
import com.rbme.apis.exception.BadRequestException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.UserMenuAccess.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    // =========================================================
    // CREATE
    // =========================================================

    public PermissionResponseDTO create(PermissionRequestDTO dto) {

        String code = normalizeCode(dto.getCode());

        if (permissionRepository.existsByCode(code)) {
            throw new BadRequestException(
                    "Permission already exists"
            );
        }

        Permission permission = new Permission();

        permission.setCode(code);
        permission.setName(dto.getName().trim());
        permission.setDescription(dto.getDescription());
        permission.setActive(
                dto.getActive() != null
                        ? dto.getActive()
                        : true
        );

        permissionRepository.save(permission);

        return convertToDTO(permission);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    public List<PermissionResponseDTO> getAll() {

        return permissionRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    public PermissionResponseDTO getById(Long id) {

        Permission permission =
                permissionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Permission not found"
                                )
                        );

        return convertToDTO(permission);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    public PermissionResponseDTO update(
            Long id,
            PermissionRequestDTO dto
    ) {

        Permission permission =
                permissionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Permission not found"
                                )
                        );

        String code = normalizeCode(dto.getCode());

        if (!permission.getCode().equals(code)
                && permissionRepository.existsByCode(code)) {

            throw new BadRequestException(
                    "Permission code already exists"
            );
        }

        permission.setCode(code);
        permission.setName(dto.getName().trim());
        permission.setDescription(dto.getDescription());
        permission.setActive(
                dto.getActive() != null
                        ? dto.getActive()
                        : true
        );

        permissionRepository.save(permission);

        return convertToDTO(permission);
    }

    // =========================================================
    // DELETE
    // =========================================================

    public void delete(Long id) {

        Permission permission =
                permissionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Permission not found"
                                )
                        );

        /*
         * Recommended:
         * Use active=false instead of physical deletion
         * when the permission is already used by roles/menus.
         */

        permission.setActive(false);

        permissionRepository.save(permission);
    }

    // =========================================================
    // NORMALIZE CODE
    // =========================================================

    private String normalizeCode(String code) {

        if (code == null || code.isBlank()) {
            throw new BadRequestException(
                    "Permission code is required"
            );
        }

        return code
                .trim()
                .toUpperCase()
                .replaceAll("[^A-Z0-9_]", "_")
                .replaceAll("_+", "_");
    }

    // =========================================================
    // DTO
    // =========================================================

    private PermissionResponseDTO convertToDTO(
            Permission permission
    ) {

        PermissionResponseDTO dto =
                new PermissionResponseDTO();

        dto.setId(permission.getId());
        dto.setCode(permission.getCode());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        dto.setActive(permission.getActive());

        return dto;
    }
}