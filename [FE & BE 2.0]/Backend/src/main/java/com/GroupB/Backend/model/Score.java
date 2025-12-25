package com.GroupB.Backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerName; // Harus sama dengan JSON Frontend
    private int score;
    private int health;

    public void setUser(User user) {
    }
}