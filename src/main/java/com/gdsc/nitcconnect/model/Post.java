package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "posts")
public class Post {
    @Id
    private Integer postId;

    private Integer igId;
    private Integer authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Getters and setters
}
