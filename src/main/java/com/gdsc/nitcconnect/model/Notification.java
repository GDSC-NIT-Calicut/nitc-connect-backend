package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    private Integer notificationId;

    private Integer postId;
    private String message;
    private LocalDateTime createdAt;

    // Getters and setters
}