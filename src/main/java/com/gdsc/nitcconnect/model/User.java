package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true, unique = true)
    private String googleId; // Nullable for email+password users

    @Column(nullable = true)
    private String password; // Nullable for Google login users

    @Column(nullable = true)
    private String email2FACode;

    @Column(nullable = true)
    private LocalDateTime email2FACodeExpiry;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Default constructor
    public User() {}

    // Constructor for Google login
    public User(String name, String email, String googleId) {
        this.name = name;
        this.email = email;
        this.googleId = googleId;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for email+password login
    public User(String name, String email, String password, boolean isEmailUser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail2FACode() {
        return email2FACode;
    }

    public void setEmail2FACode(String email2FACode) {
        this.email2FACode = email2FACode;
    }

    public LocalDateTime getEmail2FACodeExpiry() {
        return email2FACodeExpiry;
    }

    public void setEmail2FACodeExpiry(LocalDateTime email2FACodeExpiry) {
        this.email2FACodeExpiry = email2FACodeExpiry;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", googleId='" + googleId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
