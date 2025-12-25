
package com.GroupB.Backend.service;

import com.GroupB.Backend.model.Score;
import com.GroupB.Backend.model.User;
import com.GroupB.Backend.repository.PlayerScoreRepository;
import com.GroupB.Backend.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserService userService;

    @Transactional
    public void saveScore(String username, int scoreValue) {
        User user = userService.findByUsername(username);

        Score score = new Score();
        score.setUser(user);
        score.setScore(scoreValue);
        scoreRepository.save(score);
    }

    @Transactional(readOnly = true)
    public List<Score> getTopScores() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }
}