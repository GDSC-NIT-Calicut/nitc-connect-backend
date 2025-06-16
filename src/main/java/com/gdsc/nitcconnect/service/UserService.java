package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.User;
import com.gdsc.nitcconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Get user by name
    public List<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    // Check if user exists
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}