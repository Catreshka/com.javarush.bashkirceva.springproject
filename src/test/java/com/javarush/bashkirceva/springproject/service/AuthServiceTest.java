package com.javarush.bashkirceva.springproject.service;

import com.javarush.bashkirceva.springproject.model.User;
import com.javarush.bashkirceva.springproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomMetricsService customMetricsService;

    @InjectMocks
    private AuthService authService;

    @Test
    void authenticateShouldReturnUserWhenCredentialsAreValid() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("admin")).thenReturn(user);
        when(passwordEncoder.matches("admin123", "encodedPassword")).thenReturn(true);

        User result = authService.authenticate("admin", "admin123");

        assertSame(user, result);
        verify(customMetricsService, never()).incrementFailedLoginCounter();
    }

    @Test
    void authenticateShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.authenticate("unknown", "password")
        );

        assertEquals("Invalid username or password", exception.getMessage());
        verify(customMetricsService).incrementFailedLoginCounter();
        verify(passwordEncoder, never()).matches("password", null);
    }

    @Test
    void authenticateShouldThrowExceptionWhenPasswordIsInvalid() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("admin")).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.authenticate("admin", "wrongPassword")
        );

        assertEquals("Invalid username or password", exception.getMessage());
        verify(customMetricsService).incrementFailedLoginCounter();
    }
}
