package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user_notifications")
@IdClass(UserNotificationId.class)
public class UserNotification {
    @Id
    private Integer userId;

    @Id
    private Integer notificationId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime receivedAt;

    public enum Status {
        Unread, Read, Pinned, Deleted
    }

    // Constructors
    public UserNotification() {}

    public UserNotification(Integer userId, Integer notificationId, Status status, LocalDateTime receivedAt) {
        this.userId = userId;
        this.notificationId = notificationId;
        this.status = status;
        this.receivedAt = receivedAt;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    // Override equals and hashCode for proper entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNotification that = (UserNotification) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, notificationId);
    }

    @Override
    public String toString() {
        return "UserNotification{" +
                "userId=" + userId +
                ", notificationId=" + notificationId +
                ", status=" + status +
                ", receivedAt=" + receivedAt +
                '}';
    }
}