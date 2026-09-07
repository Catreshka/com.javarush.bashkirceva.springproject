package com.javarush.bashkirceva.springproject.security;

import com.javarush.bashkirceva.springproject.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    private final UserRepository userRepository;

    public UserSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isSelf(Long userId, String username) {
        return userRepository.existsByIdAndUsername(userId, username);
    }
}
