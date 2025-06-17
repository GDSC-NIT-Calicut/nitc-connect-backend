package com.gdsc.nitcconnect.model;

import java.io.Serializable;
import java.util.Objects;

public class SubscribeId implements Serializable {
    private Integer userId;
    private Integer igId;

    // Default constructor
    public SubscribeId() {}

    // Constructor with parameters
    public SubscribeId(Integer userId, Integer igId) {
        this.userId = userId;
        this.igId = igId;
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

    // equals and hashCode - REQUIRED for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribeId that = (SubscribeId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(igId, that.igId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, igId);
    }

    // toString method
    @Override
    public String toString() {
        return "SubscribeId{" +
                "userId=" + userId +
                ", igId=" + igId +
                '}';
    }
}