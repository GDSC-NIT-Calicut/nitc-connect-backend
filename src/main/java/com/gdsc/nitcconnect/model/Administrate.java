package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "administrates")
@IdClass(AdministrateId.class)   //for classes with composite primary key (check below for class implementation)
public class Administrate {
    @Id
    private Integer userId;
    @Id
    private Integer igId;

    private LocalDateTime assignedAt;
    //TODO
    // Getters and setters
}

class AdministrateId implements Serializable {
    private Integer userId;
    private Integer igId;

    // equals() and hashCode()
}