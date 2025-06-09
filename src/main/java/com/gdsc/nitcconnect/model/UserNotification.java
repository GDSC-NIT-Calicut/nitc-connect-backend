package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user_notifications")
@IdClass(UserNotificationId.class)  //for classes with composite primary key (check below for class implementation)
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
    //TODO
    // Getters and setters
}

class UserNotificationId implements Serializable {
    private Integer userId;
    private Integer notificationId;

    // equals() and hashCode()
}
