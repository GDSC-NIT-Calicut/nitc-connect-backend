package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "interest_groups")
public class InterestGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer igId;

    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Integer createdBy;

    // Getters
    public Integer getIgId() {
        return igId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    // Setters
    public void setIgId(Integer igId) {
        this.igId = igId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}