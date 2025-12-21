package main.java.com.GroupB.Backend.repository;

import com.GroupB.Backend.repository.PlayerScoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {

    @Query("SELECT p FROM PlayerScore p ORDER BY p.score DESC LIMIT 10")
    List<PlayerScore> findTop10ByOrderByScoreDesc();
}