package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "access_codes")
public class AccessCode {
    @Id
    private String code;
    private Integer igId;
    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;

    // Getters and setters
}

