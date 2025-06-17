package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "administrates")
@IdClass(AdministrateId.class)
public class Administrate {

    @Id
    private Integer userId;

    @Id
    private Integer igId;

    private LocalDateTime assignedAt;

    // Constructors
    public Administrate() {}

    public Administrate(Integer userId, Integer igId, LocalDateTime assignedAt) {
        this.userId = userId;
        this.igId = igId;
        this.assignedAt = assignedAt;
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIgId() {
        return igId;
    }

    public void setIgId(Integer igId) {
        this.igId = igId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    @Override
    public String toString() {
        return "Administrate{" +
                "userId=" + userId +
                ", igId=" + igId +
                ", assignedAt=" + assignedAt +
                '}';
    }
}