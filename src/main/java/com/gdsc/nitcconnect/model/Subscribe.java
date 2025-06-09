package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "subscribes")
@IdClass(SubscribeId.class)   //for classes with composite primary key (check below for class implementation)
public class Subscribe {
    @Id
    private Integer userId;
    @Id
    private Integer igId;

    private LocalDateTime subscribedAt;
    private Boolean isMuted;

    // Getters and setters
}

class SubscribeId implements Serializable {
    private Integer userId;
    private Integer igId;

    // equals() and hashCode()
}