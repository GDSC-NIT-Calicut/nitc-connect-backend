package com.gdsc.nitcconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private Integer igId;
    private Integer authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Getters
    public Integer getPostId() {
        return postId;
    }

    public Integer getIgId() {
        return igId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setIgId(Integer igId) {
        this.igId = igId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}