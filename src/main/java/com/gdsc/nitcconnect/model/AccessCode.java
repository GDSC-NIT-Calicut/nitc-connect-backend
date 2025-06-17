package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "access_codes")
public class AccessCode {
    @Id
    private String code;

    private Integer igId;

    private LocalDateTime createdAt;

    private LocalDateTime expiryDate;

    // Default constructor
    public AccessCode() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with parameters
    public AccessCode(String code, Integer igId, LocalDateTime expiryDate) {
        this.code = code;
        this.igId = igId;
        this.createdAt = LocalDateTime.now();
        this.expiryDate = expiryDate;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIgId() {
        return igId;
    }

    public void setIgId(Integer igId) {
        this.igId = igId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    // Utility methods
    public boolean isExpired() {
        return expiryDate != null && LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isValid() {
        return !isExpired();
    }

//    // Lifecycle callbacks
//    @PrePersist
//    protected void onCreate() {
//        if (createdAt == null) {
//            createdAt = LocalDateTime.now();
//        }
//    }

    // equals and hashCode based on primary key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessCode that = (AccessCode) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    // toString method
    @Override
    public String toString() {
        return "AccessCode{" +
                "code='" + code + '\'' +
                ", igId=" + igId +
                ", createdAt=" + createdAt +
                ", expiryDate=" + expiryDate +
                ", isExpired=" + isExpired() +
                '}';
    }
}