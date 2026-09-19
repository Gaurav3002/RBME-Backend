package com.rbme.apis.config;

import com.rbme.apis.security.JwtAuthenticationEntryPoint;
import com.rbme.apis.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

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


    // =========================================================
    // PASSWORD ENCODER
    // =========================================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // =========================================================
    // SECURITY FILTER CHAIN
    // =========================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // =================================================
                // CSRF
                // =================================================

                .csrf(csrf ->
                        csrf.disable()
                )


                // =================================================
                // CORS
                // =================================================

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )


                // =================================================
                // SESSION
                // =================================================

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // =================================================
                // EXCEPTION HANDLING
                // =================================================

                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )


                // =================================================
                // AUTHORIZATION
                // =================================================

                .authorizeHttpRequests(auth -> auth


                        // =================================================
                        // PUBLIC STATIC FILES
                        // =================================================

                        .requestMatchers(
                                "/uploads/**",
                                "/product/images/**",
                                "/product/thumbnail/**",
                                "/product/documents/**",
                                "/company/logo/**",
                                "/company/banner/**"
                        )
                        .permitAll()


                        // =================================================
                        // PUBLIC ADMIN LOGIN
                        // =================================================

                        .requestMatchers(
                                "/api/admin/auth/**"
                        )
                        .permitAll()


                        // =================================================
                        // PUBLIC COMPANY APIs
                        // =================================================

                        .requestMatchers(
                                "/api/companies/**"
                        )
                        .permitAll()


                        // =================================================
                        // PUBLIC PRODUCT APIs
                        // =================================================

                        .requestMatchers(
                                "/api/products/**"
                        )
                        .permitAll()


                        // =================================================
                        // PUBLIC PROJECT ENQUIRY CREATE ONLY
                        // =================================================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/project-enquiries"
                        )
                        .permitAll()


                        // =================================================
                        // ADMIN COMPANY APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/companies/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN CATEGORY APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/categories/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN PRODUCT TYPE APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/product-types/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN PRODUCT SPECIFICATION APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/product-specifications/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN PRODUCT APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/products/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN USER APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/users/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN ROLE APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/roles/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN PERMISSION APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/permissions/**"
                        )
                        .authenticated()


                        // =================================================
                        // ADMIN MENU APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/menus/**"
                        )
                        .authenticated()


                        // =================================================
                        // COMPANY INFORMATION
                        // =================================================

                        .requestMatchers(
                                "/api/admin/companyinfo/**"
                        )
                        .authenticated()


                        // =================================================
                        // BANK DETAILS
                        // =================================================

                        .requestMatchers(
                                "/api/admin/bankdetails/**"
                        )
                        .authenticated()


                        // =================================================
                        // TAX INFORMATION
                        // =================================================

                        .requestMatchers(
                                "/api/admin/taxinfoDetails/**"
                        )
                        .authenticated()


                        // =================================================
                        // DOCUMENT SETTINGS
                        // =================================================

                        .requestMatchers(
                                "/api/admin/document-settings/**"
                        )
                        .authenticated()


                        // =================================================
                        // PROJECT ENQUIRY ADMIN OPERATIONS
                        // =================================================

                        .requestMatchers(
                                "/api/project-enquiries/**"
                        )
                        .authenticated()


                        // =================================================
                        // OTHER ADMIN APIs
                        // =================================================

                        .requestMatchers(
                                "/api/admin/**"
                        )
                        .hasRole("ADMIN")


                        // =================================================
                        // EVERYTHING ELSE
                        // =================================================

                        .anyRequest()
                        .authenticated()
                )


                // =================================================
                // JWT FILTER
                // =================================================

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

        CorsConfiguration configuration =
                new CorsConfiguration();


        // =========================================================
        // ALLOWED ORIGINS
        // =========================================================

        configuration.setAllowedOrigins(

                Arrays.stream(
                                allowedOrigins.split(",")
                        )
                        .map(String::trim)
                        .filter(
                                origin -> !origin.isEmpty()
                        )
                        .toList()
        );


        // =========================================================
        // ALLOWED METHODS
        // =========================================================

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


        // =========================================================
        // ALLOWED HEADERS
        // =========================================================

        configuration.setAllowedHeaders(
                List.of("*")
        );


        // =========================================================
        // EXPOSED HEADERS
        // =========================================================

        configuration.setExposedHeaders(
                List.of("Authorization")
        );


        // =========================================================
        // ALLOW CREDENTIALS
        // =========================================================

        configuration.setAllowCredentials(true);


        // =========================================================
        // PREFLIGHT CACHE
        // =========================================================

        configuration.setMaxAge(3600L);


        // =========================================================
        // REGISTER CORS
        // =========================================================

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;
    }
}
