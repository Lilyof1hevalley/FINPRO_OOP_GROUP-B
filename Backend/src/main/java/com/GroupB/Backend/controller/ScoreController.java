package main.java.com.GroupB.Backend.controller;

import com.GroupB.Backend.model.PlayerScore;
import com.GroupB.Backend.repository.PlayerScoreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ScoreController {

    private final PlayerScoreRepository scoreRepository;

    //Acc data from game
    @PostMapping
    public ResponseEntity<String> submitScore(@RequestBody ScoreSubmissionRequest request) {
        try {
            PlayerScore score = new PlayerScore();
            score.setPlayerName(request.getPlayerName());
            score.setScore(request.getScore());
            score.setHealth(request.getHealth());
            scoreRepository.save(score);
            return ResponseEntity.ok("Score saved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to save score: " + e.getMessage());
        }
    }

    //Take leaderboard
    @GetMapping("/leaderboard")
    public ResponseEntity<List<PlayerScore>> getLeaderboard() {
        return ResponseEntity.ok(scoreRepository.findTop10ByOrderByScoreDesc());
    }

    //DTO to acc JSON
    @Data
    public static class ScoreSubmissionRequest {
        private String playerName;
        private int score;
        private int health;
    }

