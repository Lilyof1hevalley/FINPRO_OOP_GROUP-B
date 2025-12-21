// backend/src/main/java/com/example/spaceshooter/repository/ScoreRepository.java
package com.example.space_shooter_backend.repository;

import com.example.space_shooter_backend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s ORDER BY s.score DESC LIMIT 10")
    List<Score> findTop10ByOrderByScoreDesc();
}