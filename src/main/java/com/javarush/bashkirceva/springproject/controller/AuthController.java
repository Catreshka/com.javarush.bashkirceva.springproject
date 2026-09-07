package com.javarush.bashkirceva.springproject.controller;

import com.javarush.bashkirceva.springproject.config.JwtTokenUtil;
import com.javarush.bashkirceva.springproject.dto.AuthRequest;
import com.javarush.bashkirceva.springproject.dto.AuthResponse;
import com.javarush.bashkirceva.springproject.dto.RegisterRequest;
import com.javarush.bashkirceva.springproject.model.User;
import com.javarush.bashkirceva.springproject.service.AuthService;
import com.javarush.bashkirceva.springproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@Valid @RequestBody AuthRequest authRequest) {
        User user = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        String token = jwtTokenUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setRole("USER");

        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
