package com.GroupB.Backend.controller;

import com.GroupB.Backend.model.PlayerScore;
import com.GroupB.Backend.repository.PlayerScoreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/score")
@AllArgsConstructor
public class ScoreController {

    private final PlayerScoreRepository scoreRepository;

    @PostMapping
    public ResponseEntity<String> submitScore(@RequestBody ScoreSubmissionRequest request) {

        System.out.println("Menerima skor dari: " + request.getPlayerName() + " | Skor: " + request.getScore());

        try {
            PlayerScore score = new PlayerScore();
            score.setPlayerName(request.getPlayerName());
            score.setScore(request.getScore());
            score.setHealth(request.getHealth());
            scoreRepository.save(score);
            return ResponseEntity.ok("Score saved successfully to Neon!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Gagal simpan ke Neon: " + e.getMessage());
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<PlayerScore>> getLeaderboard() {
        return ResponseEntity.ok(scoreRepository.findAll()); // Ambil semua dulu buat ngetes
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScoreSubmissionRequest {
        private String playerName;
        private int score;
        private int health;
    }
}