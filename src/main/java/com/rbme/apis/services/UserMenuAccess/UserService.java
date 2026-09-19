package com.rbme.apis.services.UserMenuAccess;

import com.rbme.apis.dto.UserMenuAccess.UserRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.UserResponseDTO;
import com.rbme.apis.entity.UserMenuAccess.Role;
import com.rbme.apis.entity.UserMenuAccess.User;
import com.rbme.apis.exception.BadRequestException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.repository.UserMenuAccess.RoleRepository;
import com.rbme.apis.repository.UserMenuAccess.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // =========================================================
    // CREATE USER
    // =========================================================

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {

        if (userRepository.existsByUsername(
                dto.getUsername()
        )) {
            throw new BadRequestException(
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmail(
                dto.getEmail()
        )) {
            throw new BadRequestException(
                    "Email already exists"
            );
        }

        Role role =
                roleRepository
                        .findById(dto.getRoleId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found"
                                )
                        );

        if (!Boolean.TRUE.equals(role.getActive())) {
            throw new BadRequestException(
                    "Cannot assign inactive role"
            );
        }

        User user = new User();

        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );
        user.setRole(role);
        user.setActive(
                dto.getActive() != null
                        ? dto.getActive()
                        : true
        );

        userRepository.save(user);

        return convertToDTO(user);
    }

    // =========================================================
    // GET ALL
    // =========================================================

    public List<UserResponseDTO> getAll() {

        return userRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    public UserResponseDTO getById(Long id) {

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return convertToDTO(user);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Transactional
    public UserResponseDTO update(
            Long id,
            UserRequestDTO dto
    ) {

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        Role role =
                roleRepository
                        .findById(dto.getRoleId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role not found"
                                )
                        );

        if (!Boolean.TRUE.equals(role.getActive())) {
            throw new BadRequestException(
                    "Cannot assign inactive role"
            );
        }

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRole(role);

        if (dto.getActive() != null) {
            user.setActive(dto.getActive());
        }

        if (dto.getPassword() != null &&
                !dto.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            dto.getPassword()
                    )
            );
        }

        userRepository.save(user);

        return convertToDTO(user);
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Transactional
    public void delete(Long id) {

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        user.setActive(false);

        userRepository.save(user);
    }

    // =========================================================
    // DTO
    // =========================================================

    private UserResponseDTO convertToDTO(User user) {

        UserResponseDTO dto =
                new UserResponseDTO();

        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        if (user.getRole() != null) {

            dto.setRoleId(
                    user.getRole().getId()
            );

            dto.setRoleName(
                    user.getRole().getName()
            );
        }

        dto.setActive(user.getActive());
        dto.setLastLogin(user.getLastLogin());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}