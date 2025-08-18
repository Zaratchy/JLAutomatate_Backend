package com.jlautomate.jlautomate_backend.controller;

import com.jlautomate.jlautomate_backend.dto.LoginRequest;
import com.jlautomate.jlautomate_backend.dto.LoginResponse;
import com.jlautomate.jlautomate_backend.dto.RegisterRequest;
import com.jlautomate.jlautomate_backend.model.User;
import com.jlautomate.jlautomate_backend.service.UserService;
import com.jlautomate.jlautomate_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest req) {
        User user = userService.authenticateUser(req.getEmail(), req.getPassword());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        String token = jwtUtil.generateToken(user.getEmail());

        LoginResponse.UserDto userDto = LoginResponse.UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles().split(","))
                .build();

        LoginResponse res = LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .user(userDto)
                .build();

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody RegisterRequest req) {
        User newUser = User.builder()
                .email(req.getEmail())
                .password(req.getPassword()) // l'encodage se fait dans UserService
                .roles("USER")
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .build();

        User saved = userService.registerUser(newUser);

        String token = jwtUtil.generateToken(saved.getEmail());

        LoginResponse.UserDto userDto = LoginResponse.UserDto.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .roles(saved.getRoles().split(","))
                .build();

        LoginResponse res = LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .user(userDto)
                .build();

        return ResponseEntity.ok(res);
    }
}
