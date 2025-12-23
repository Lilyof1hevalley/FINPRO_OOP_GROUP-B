package com.groupB.Frontend.manager;

import com.groupB.Frontend.entity.Enemy;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance;

    private String currentUser;
    private int currentScore;
    private List<Enemy> enemies;

    private GameManager() {
        enemies = new ArrayList<>();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void reset(String username, int initialScore) {
        this.currentUser = username;
        this.currentScore = initialScore;
        this.enemies.clear();
    }

    public int getScore() {
        return currentScore;
    }

    public void addScore(int points) {
        this.currentScore += points;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
}
