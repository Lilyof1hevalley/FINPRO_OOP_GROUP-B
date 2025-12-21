// backend/src/main/java/com/example/spaceshooter/service/UserService.java
package main.java.com.GroupB.Backend.service;

import main.java.com.GroupB.Backend.model.User;
import main.java.com.GroupB.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        // In production: use BCryptPasswordEncoder
        User user = new User();
        user.setUsername(username);
        user.setPassword(rawPassword); // ⚠️ Harus di-hash!
        userRepository.save(user);
    }

    public boolean authenticate(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .map(u -> u.getPassword().equals(rawPassword)) // ⚠️ hanya untuk demo
                .orElse(false);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}