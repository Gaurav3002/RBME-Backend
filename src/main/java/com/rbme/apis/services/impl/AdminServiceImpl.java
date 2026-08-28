package com.rbme.apis.service.impl;

import com.rbme.apis.dto.admin.AdminCreateRequest;
import com.rbme.apis.dto.admin.AdminLoginRequest;
import com.rbme.apis.dto.admin.AdminLoginResponse;
import com.rbme.apis.dto.admin.AdminResponse;
import com.rbme.apis.dto.admin.AdminUpdateRequest;
import com.rbme.apis.entity.Admin;
import com.rbme.apis.exception.DuplicateResourceException;
import com.rbme.apis.exception.ResourceNotFoundException;
import com.rbme.apis.exception.UnauthorizedException;
import com.rbme.apis.repository.AdminRepository;
import com.rbme.apis.security.JwtService;
import com.rbme.apis.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {

        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password"));

        if (!Boolean.TRUE.equals(admin.getActive())) {
            throw new UnauthorizedException("Admin account is disabled.");
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        admin.setLastLogin(LocalDateTime.now());
        adminRepository.save(admin);

        String token = jwtService.generateToken(admin.getEmail());

        return new AdminLoginResponse(
                token,
                mapToResponse(admin)
        );
    }

    @Override
    public AdminResponse createAdmin(AdminCreateRequest request) {

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists.");
        }

        Admin admin = new Admin();

        admin.setFullName(request.getFullName());
        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() == null || request.getRole().isBlank()) {
            admin.setRole("ROLE_ADMIN");
        } else {
            admin.setRole(request.getRole());
        }

        admin.setActive(true);

        admin = adminRepository.save(admin);

        return mapToResponse(admin);
    }

    @Override
    public AdminResponse updateAdmin(Long id, AdminUpdateRequest request) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found."));

        if (request.getFullName() != null) {
            admin.setFullName(request.getFullName());
        }

        if (request.getUsername() != null
                && !request.getUsername().equals(admin.getUsername())) {

            if (adminRepository.existsByUsername(request.getUsername())) {
                throw new DuplicateResourceException("Username already exists.");
            }

            admin.setUsername(request.getUsername());
        }

        if (request.getEmail() != null
                && !request.getEmail().equals(admin.getEmail())) {

            if (adminRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Email already exists.");
            }

            admin.setEmail(request.getEmail());
        }

        if (request.getActive() != null) {
            admin.setActive(request.getActive());
        }

        admin = adminRepository.save(admin);

        return mapToResponse(admin);
    }

    @Override
    public AdminResponse getAdminById(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found."));

        return mapToResponse(admin);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {

        return adminRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteAdmin(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found."));

        adminRepository.delete(admin);
    }

    @Override
    public void updateStatus(Long id, Boolean active) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found."));

        admin.setActive(active);

        adminRepository.save(admin);
    }

    /**
     * Entity -> Response DTO
     */
    private AdminResponse mapToResponse(Admin admin) {

        return AdminResponse.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole())
                .active(admin.getActive())
                .lastLogin(admin.getLastLogin())
                .createdAt(admin.getCreatedAt())
                .build();
    }
}