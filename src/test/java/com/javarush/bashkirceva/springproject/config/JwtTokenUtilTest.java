package com.javarush.bashkirceva.springproject.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenUtilTest {

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Test
    void generateTokenShouldCreateValidToken() {
        String token = jwtTokenUtil.generateToken("admin");

        assertTrue(jwtTokenUtil.validateToken(token));
    }

    @Test
    void extractUsernameShouldReturnTokenSubject() {
        String token = jwtTokenUtil.generateToken("admin");

        String username = jwtTokenUtil.extractUsername(token);

        assertEquals("admin", username);
    }

    @Test
    void validateTokenShouldReturnFalseForInvalidToken() {
        boolean result = jwtTokenUtil.validateToken("invalid.token.value");

        assertFalse(result);
    }
}
