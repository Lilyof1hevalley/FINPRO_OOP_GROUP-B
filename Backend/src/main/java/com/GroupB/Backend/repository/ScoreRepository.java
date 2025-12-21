
package com.GroupB.Backend.repository;

import com.GroupB.Backend.repository.ScoreRepository;
import org.springframework.data.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s ORDER BY s.score DESC LIMIT 10")
    List<Score> findTop10ByOrderByScoreDesc();
}