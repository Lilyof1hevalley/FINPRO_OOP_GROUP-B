
package com.GroupB.Backend.service;

import com.GroupB.Backend.model.User;
import com.GroupB.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(String username, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(rawPassword);
        userRepository.save(user);
    }

    public boolean authenticate(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(rawPassword))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}