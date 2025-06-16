package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "subscribes")
@IdClass(SubscribeId.class)
public class Subscribe {
    @Id
    private Integer userId;

    @Id
    private Integer igId;

    private LocalDateTime subscribedAt;

    private Boolean isMuted;

    public Subscribe() {
        this.subscribedAt = LocalDateTime.now();
        this.isMuted = false;
    }

    public Subscribe(Integer userId, Integer igId) {
        this.userId = userId;
        this.igId = igId;
        this.subscribedAt = LocalDateTime.now();
        this.isMuted = false;
    }

    public Subscribe(Integer userId, Integer igId, Boolean isMuted) {
        this.userId = userId;
        this.igId = igId;
        this.subscribedAt = LocalDateTime.now();
        this.isMuted = isMuted;
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

    public LocalDateTime getSubscribedAt() {
        return subscribedAt;
    }

    public void setSubscribedAt(LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public Boolean getIsMuted() {
        return isMuted;
    }

    public void setIsMuted(Boolean isMuted) {
        this.isMuted = isMuted;
    }

    // Utility methods
    public boolean isMuted() {
        return isMuted != null && isMuted;
    }

    public void mute() {
        this.isMuted = true;
    }

    public void unmute() {
        this.isMuted = false;
    }


    // equals and hashCode based on composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscribe subscribe = (Subscribe) o;
        return Objects.equals(userId, subscribe.userId) &&
                Objects.equals(igId, subscribe.igId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, igId);
    }

    // toString method
    @Override
    public String toString() {
        return "Subscribe{" +
                "userId=" + userId +
                ", igId=" + igId +
                ", subscribedAt=" + subscribedAt +
                ", isMuted=" + isMuted +
                '}';
    }
}

