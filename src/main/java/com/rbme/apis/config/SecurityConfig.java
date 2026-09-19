package com.rbme.apis.config;

import com.rbme.apis.security.JwtAuthenticationEntryPoint;
import com.rbme.apis.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/product/images/**",
                                "/product/thumbnail/**",
                                "/product/documents/**",
                                "/company/logo/**",
                                "/company/banner/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/admin/auth/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/project-enquiries"
                        ).permitAll()

                        // Company
                        .requestMatchers(
                                "/api/admin/companies/**"
                        ).authenticated()

                        // Category
                        .requestMatchers(
                                "/api/admin/categories/**"
                        ).authenticated()

                        // Product Type
                        .requestMatchers(
                                "/api/admin/product-types/**"
                        ).authenticated()

                        // Product Specification
                        .requestMatchers(
                                "/api/admin/product-specifications/**"
                        ).authenticated()

                        // Product
                        .requestMatchers(
                                "/api/admin/products/**"
                        ).authenticated()

                        // Users
                        .requestMatchers(
                                "/api/admin/users/**"
                        ).authenticated()

                        // Roles
                        .requestMatchers(
                                "/api/admin/roles/**"
                        ).authenticated()

                        // Permissions
                        .requestMatchers(
                                "/api/admin/permissions/**"
                        ).authenticated()

                        // Menus
                        .requestMatchers(
                                "/api/admin/menus/**"
                        ).authenticated()
                        .requestMatchers(
                                "/api/admin/companyinfo/**"
                        ).authenticated()
                        .requestMatchers(
                                "/api/admin/bankdetails/**"
                        ).authenticated()
                        .requestMatchers(
                                "/api/admin/taxinfoDetails/**"
                        ).authenticated()
                        .requestMatchers(
                                "/api/admin/document-settings/**"
                        ).authenticated()

                        // Existing non-admin APIs
                        .requestMatchers(
                                "/api/categories/**",
                                "/api/types/**",
                                "/api/products/**"
                        ).authenticated()

                        // Other admin APIs
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                Arrays.stream(allowedOrigins.split(","))
                        .map(String::trim)
                        .filter(origin -> !origin.isEmpty())
                        .toList()
        );

        configuration.setAllowedMethods(
                Arrays.asList(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        configuration.setAllowCredentials(true);

        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}