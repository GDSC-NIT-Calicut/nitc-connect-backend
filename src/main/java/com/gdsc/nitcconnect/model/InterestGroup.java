package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "interest_groups")
public class InterestGroup {
    @Id
    private Integer igId;

    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Integer createdBy;

    // Getters and setters
}

