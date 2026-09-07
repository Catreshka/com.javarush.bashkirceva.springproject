package com.javarush.bashkirceva.springproject.service;

import com.javarush.bashkirceva.springproject.model.User;
import com.javarush.bashkirceva.springproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomMetricsService customMetricsService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       CustomMetricsService customMetricsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customMetricsService = customMetricsService;
    }

    @Transactional(readOnly = true)
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            customMetricsService.incrementFailedLoginCounter();
            throw new IllegalArgumentException("Invalid username or password");
        }

        return user;
    }
}
