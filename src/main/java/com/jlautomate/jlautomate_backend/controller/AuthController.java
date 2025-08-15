package com.jlautomate.jlautomate_backend.controller;

import com.jlautomate.jlautomate_backend.dto.LoginRequest;
import com.jlautomate.jlautomate_backend.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true") // dev
public class AuthController {

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest req) {
        // TODO: remplacer plus tard par la vraie authentification (DB + JWT)
        if (req.getEmail() == null || req.getPassword() == null || req.getEmail().isBlank() || req.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        LoginResponse res = new LoginResponse();
        res.setAccessToken(UUID.randomUUID().toString()); // token fictif pour tester
        LoginResponse.UserDto user = new LoginResponse.UserDto();
        user.setId(1L);
        user.setEmail(req.getEmail());
        user.setRoles(new String[]{"USER"});
        res.setUser(user);

        return ResponseEntity.ok(res);
    }
}
