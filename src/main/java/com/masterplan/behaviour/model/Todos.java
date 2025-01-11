package com.masterplan.behaviour.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table("todos")
public class Todos {

    @Id
    @Column("id")
    private Integer id;

    @Column("description")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @Column("title")
    private String title;

    @Column("user_id")
    @NotNull(message = "User ID is required")
    private Integer userId;

    @Column("status")
    @NotNull(message = "Status is required")
    private String status = "pending";

    @Column("category")
    @NotNull(message = "Category is required")
    private String category = "personal";

    @Column("stared")
    private boolean stared = false;

    @Column("target_at")
    @JsonProperty("target_at")
    private LocalDateTime targetAt;

    @Column("rating")
    @NotEmpty(message = "Rating is required")
    private Integer rating;

    @Column("completed_at")
    private LocalDateTime completedDate = null;

    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column("updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Default constructor
    public Todos() {}

    // Parameterized constructor
    public Todos(Integer id, String description, String title, Integer userId, Integer rating, 
                 LocalDateTime completedDate, String status, LocalDateTime createdAt, 
                 LocalDateTime updatedAt, String category, boolean stared, LocalDateTime targetAt) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.title = title;
        this.rating = rating;
        this.completedDate = completedDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.targetAt = targetAt;
        this.category = category;
        this.stared = stared;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public LocalDateTime getTargetAt() {
        return targetAt;
    }

    public void setTargetAt(LocalDateTime targetAt) {
        this.targetAt = targetAt;
    }

    @Override
    public String toString() {
        return "Todos: ";
    }
}
