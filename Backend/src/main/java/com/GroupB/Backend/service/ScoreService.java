// backend/src/main/java/com/example/spaceshooter/service/ScoreService.java
package main.java.com.GroupB.Backend.service;

import main.java.com.GroupB.Backend.model.Score;
import main.java.com.GroupB.Backend.model.User;
import main.java.com.GroupB.Backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserService userService;

    public void saveScore(String username, int scoreValue) {
        User user = userService.findByUsername(username);
        Score score = new Score();
        score.setUser(user);
        score.setScore(scoreValue);
        scoreRepository.save(score);
    }

    public List<Score> getTopScores() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }
}