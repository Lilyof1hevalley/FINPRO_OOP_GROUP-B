// backend/src/main/java/com/example/spaceshooter/repository/UserRepository.java
package com.example.space_shooter_backend.repository;

import com.example.space_shooter_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}