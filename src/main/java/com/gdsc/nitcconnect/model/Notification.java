package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    private Integer postId;
    private String message;
    private LocalDateTime createdAt;

    // Getters
    public Integer getNotificationId() {
        return notificationId;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}