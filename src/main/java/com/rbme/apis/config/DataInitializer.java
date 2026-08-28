package com.rbme.apis.config;

import com.rbme.apis.entity.Admin;
import com.rbme.apis.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (adminRepository.count() == 0) {

            Admin admin = new Admin();

            admin.setFullName("Super Admin");
            admin.setUsername("admin");
            admin.setEmail("admin@rbme.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole("ROLE_ADMIN");
            admin.setActive(true);

            adminRepository.save(admin);

            System.out.println("Default Admin Created.");
        }
    }
}