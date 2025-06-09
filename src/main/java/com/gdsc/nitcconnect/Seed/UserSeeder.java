package com.gdsc.nitcconnect.Seed;

import com.gdsc.nitcconnect.model.User;
import com.gdsc.nitcconnect.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // For proper password hashing

    @PostConstruct
    public void addUsers() {
        if (userRepository.count() == 0) {
            // Create users with proper password hashing
            User user1 = new User("Alice", "alice@example.com",
                    passwordEncoder.encode("password123"));

            User user2 = new User("Bob", "bob@example.com",
                    passwordEncoder.encode("password456"));

            userRepository.save(user1);
            userRepository.save(user2);

            System.out.println("Seeded 2 users.");
        } else {
            System.out.println("Users already exist. Skipping seeding.");
        }
    }
}