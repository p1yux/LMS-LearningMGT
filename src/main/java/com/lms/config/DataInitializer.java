package com.lms.config;

import com.lms.model.User;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            if (!userRepository.existsByEmail("admin@lms.com")) {
                log.info("Creating admin user...");
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@lms.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(User.UserRole.ADMIN);
                userRepository.save(admin);
                log.info("Admin user created successfully!");
            } else {
                log.info("Admin user already exists");
                // Update admin password if needed
                User admin = userRepository.findByEmail("admin@lms.com")
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
                admin.setPassword(passwordEncoder.encode("admin123"));
                userRepository.save(admin);
                log.info("Admin password updated");
            }
        } catch (Exception e) {
            log.error("Error initializing admin user", e);
        }
    }
} 