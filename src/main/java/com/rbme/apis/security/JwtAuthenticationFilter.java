package com.rbme.apis.security;

import com.rbme.apis.entity.UserMenuAccess.User;
import com.rbme.apis.repository.UserMenuAccess.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String token = getTokenFromRequest(request);

            if (token != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                String username =
                        jwtService.extractUsername(token);

                if (username != null) {

                    User user =
                            userRepository
                                    .findByUsername(username)
                                    .orElse(null);

                    if (user != null
                            && Boolean.TRUE.equals(user.getActive())
                            && jwtService.isTokenValid(
                            token,
                            user.getUsername())) {

                        List<SimpleGrantedAuthority> authorities =
                                new ArrayList<>();

                        /*
                         * ROLE
                         */
                        if (user.getRole() != null) {

                            authorities.add(
                                    new SimpleGrantedAuthority(
                                            "ROLE_" +
                                                    user.getRole().getName()
                                    )
                            );

                            /*
                             * PERMISSIONS
                             */
                            if (user.getRole().getPermissions() != null) {

                                user.getRole()
                                        .getPermissions()
                                        .stream()
                                        .filter(permission ->
                                                Boolean.TRUE.equals(
                                                        permission.getActive()
                                                )
                                        )
                                        .forEach(permission ->
                                                authorities.add(
                                                        new SimpleGrantedAuthority(
                                                                permission.getCode()
                                                        )
                                                )
                                        );
                            }
                        }

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        authorities
                                );

                        authentication.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request)
                        );

                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authentication);
                    }
                }
            }

        } catch (Exception ex) {

            /*
             * Invalid / expired JWT.
             * Continue without authentication.
             */
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(
            HttpServletRequest request) {

        String bearerToken =
                request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith("Bearer ")) {

            return bearerToken.substring(7);
        }

        return null;
    }
}