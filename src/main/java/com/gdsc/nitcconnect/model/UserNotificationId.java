package com.gdsc.nitcconnect.model;

import java.io.Serializable;
import java.util.Objects;

public class UserNotificationId implements Serializable {
    private Integer userId;
    private Integer notificationId;

    // Default constructor
    public UserNotificationId() {}

    // Parameterized constructor
    public UserNotificationId(Integer userId, Integer notificationId) {
        this.userId = userId;
        this.notificationId = notificationId;
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

    // Override equals and hashCode (required for composite keys)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNotificationId that = (UserNotificationId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, notificationId);
    }

    @Override
    public String toString() {
        return "UserNotificationId{" +
                "userId=" + userId +
                ", notificationId=" + notificationId +
                '}';
    }
}
