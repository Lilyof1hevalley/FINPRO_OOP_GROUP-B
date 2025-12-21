// backend/src/main/java/com/example/spaceshooter/repository/PlayerScoreRepository.java
package com.example.space_shooter_backend.repository;

import com.example.space_shooter_backend.model.PlayerScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {

    @Query("SELECT p FROM PlayerScore p ORDER BY p.score DESC LIMIT 10")
    List<PlayerScore> findTop10ByOrderByScoreDesc();
}