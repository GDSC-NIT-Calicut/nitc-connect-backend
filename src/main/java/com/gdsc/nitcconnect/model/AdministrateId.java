package com.gdsc.nitcconnect.model;

import java.io.Serializable;
import java.util.Objects;

public class AdministrateId implements Serializable {
    private Integer userId;
    private Integer igId;

    // Constructors
    public AdministrateId() {}

    public AdministrateId(Integer userId, Integer igId) {
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

    // equals() and hashCode() - Required for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdministrateId that = (AdministrateId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(igId, that.igId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, igId);
    }

    @Override
    public String toString() {
        return "AdministrateId{" +
                "userId=" + userId +
                ", igId=" + igId +
                '}';
    }
}