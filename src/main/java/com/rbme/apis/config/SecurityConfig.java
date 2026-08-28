package com.rbme.apis.config;

import com.rbme.apis.security.JwtAuthenticationEntryPoint;
import com.rbme.apis.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // =========================================================
                // CSRF
                // =========================================================
                .csrf(csrf -> csrf.disable())

                // =========================================================
                // CORS
                // =========================================================
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // =========================================================
                // SESSION
                // =========================================================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // =========================================================
                // EXCEPTION HANDLING
                // =========================================================
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )

                // =========================================================
                // AUTHORIZATION
                // =========================================================
                .authorizeHttpRequests(auth -> auth

                        // -------------------------------------------------
                        // PUBLIC FILES
                        // -------------------------------------------------
                        .requestMatchers(
                                "/product/images/**",
                                "/product/thumbnail/**",
                                "/product/documents/**",
                                "/company/logo/**",
                                "/company/banner/**"
                        ).permitAll()

                        // -------------------------------------------------
                        // PUBLIC AUTH APIs
                        // -------------------------------------------------
                        .requestMatchers(
                                "/api/admin/auth/**"
                        ).permitAll()

                        // -------------------------------------------------
                        // PUBLIC COMPANY APIs
                        // -------------------------------------------------
                        .requestMatchers(
                                "/api/companies/**"
                        ).permitAll()

                        // -------------------------------------------------
                        // PUBLIC CATEGORY APIs
                        // -------------------------------------------------
                        .requestMatchers(
                                "/api/categories/**"
                        ).permitAll()

                        // -------------------------------------------------
                        // PUBLIC PRODUCT TYPE APIs
                        // -------------------------------------------------
                        .requestMatchers(
                                "/api/types/**"
                        ).permitAll()

                        // -------------------------------------------------
                        // PUBLIC PRODUCT APIs
                        // -------------------------------------------------
                        .requestMatchers(
                                "/api/products/**"
                        ).permitAll()

                        // -------------------------------------------------
                        // PUBLIC PROJECT ENQUIRY
                        //
                        // IMPORTANT:
                        // Contact/Enquiry form does NOT need JWT.
                        // -------------------------------------------------
                        .requestMatchers(
                                "/api/project-enquiries"
                        ).permitAll()

                        // -------------------------------------------------
                        // ADMIN APIs
                        // -------------------------------------------------
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        // -------------------------------------------------
                        // EVERYTHING ELSE
                        // -------------------------------------------------
                        .anyRequest().authenticated()
                )

                // =========================================================
                // JWT FILTER
                // =========================================================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // =========================================================
    // CORS CONFIGURATION
    // =========================================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:8080"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of(
                "*"
        ));

        configuration.setExposedHeaders(List.of(
                "Authorization"
        ));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    // =========================================================
    // PASSWORD ENCODER
    // =========================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =========================================================
    // AUTHENTICATION MANAGER
    // =========================================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }
}