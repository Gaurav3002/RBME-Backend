package com.rbme.apis.services.impl;

import com.rbme.apis.dto.UserMenuAccess.LoginRequestDTO;
import com.rbme.apis.dto.UserMenuAccess.LoginResponseDTO;
import com.rbme.apis.entity.UserMenuAccess.Permission;
import com.rbme.apis.entity.UserMenuAccess.User;
import com.rbme.apis.exception.UnauthorizedException;
import com.rbme.apis.repository.UserMenuAccess.UserRepository;
import com.rbme.apis.security.JwtService;
import com.rbme.apis.services.AuthService;
import com.rbme.apis.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;



    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {



        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new UnauthorizedException(
                                "Invalid username or password"
                        )
                );


        // ---------------------------------------------------------
        // Check User Active
        // ---------------------------------------------------------

        if (!Boolean.TRUE.equals(user.getActive())) {

            throw new UnauthorizedException(
                    "User account is disabled."
            );
        }


        // ---------------------------------------------------------
        // Check Password
        // ---------------------------------------------------------

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new UnauthorizedException(
                    "Invalid username or password"
            );
        }


        // ---------------------------------------------------------
        // Update Last Login
        // ---------------------------------------------------------

        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);


        // ---------------------------------------------------------
        // Generate JWT
        // ---------------------------------------------------------

        String token = jwtService.generateToken(
                user.getUsername()
        );


        // ---------------------------------------------------------
        // Get Permissions
        // ---------------------------------------------------------

        Set<String> permissions = Set.of();

        if (user.getRole() != null
                && user.getRole().getPermissions() != null) {

            permissions = user.getRole()
                    .getPermissions()
                    .stream()
                    .filter(permission ->
                            Boolean.TRUE.equals(
                                    permission.getActive()
                            )
                    )
                    .map(Permission::getCode)
                    .collect(Collectors.toSet());
        }

        String roleName = null;

        if (user.getRole() != null) {

            roleName = user.getRole().getName();
        }


        LoginResponseDTO response = new LoginResponseDTO();

        response.setToken(token);
        response.setUserId(user.getId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setRole(roleName);
        response.setPermissions(permissions);

        return response;
    }
}