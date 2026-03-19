package com.ayubot.controller;

import com.ayubot.model.User;
import com.ayubot.service.UserService;
import com.ayubot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * User registration endpoint
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Validate input
            if (request.name == null || request.name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Name is required"));
            }
            if (request.email == null || request.email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
            }
            if (request.password == null || request.password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Password is required"));
            }
            if (request.role == null || request.role.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Role is required"));
            }
            
            // Validate role (allow ADMIN in addition to PATIENT/DOCTOR)
            String role = request.role.toUpperCase();
            if (!role.equals("PATIENT") && !role.equals("DOCTOR") && !role.equals("ADMIN")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Role must be PATIENT, DOCTOR or ADMIN"));
            }

            // Create new user
            User newUser = new User();
            newUser.setName(request.name.trim());
            newUser.setEmail(request.email.trim().toLowerCase());
            newUser.setPassword(request.password);
            newUser.setRole(role);

            // Register user (password will be encrypted in service)
            User savedUser = userService.registerUser(newUser);

            // Generate JWT token
            String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole(), savedUser.getId());

            // Return response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", savedUser.getRole());
            response.put("email", savedUser.getEmail());
            response.put("name", savedUser.getName());
            response.put("userId", savedUser.getId());
            response.put("message", "Registration successful");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    /**
     * User login endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Validate input
            if (request.email == null || request.email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
            }
            if (request.password == null || request.password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Password is required"));
            }

            // Authenticate user
            User user = userService.authenticateUser(request.email.trim().toLowerCase(), request.password);

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());

            // Return response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("userId", user.getId());
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }

    /**
     * Verify token endpoint (optional - for checking if token is still valid)
     */
    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid authorization header"));
            }

            String token = authHeader.substring(7);
            boolean isValid = jwtUtil.validateToken(token);

            if (isValid) {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("email", jwtUtil.extractEmail(token));
                response.put("role", jwtUtil.extractRole(token));
                response.put("userId", jwtUtil.extractUserId(token));
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("valid", false, "error", "Token is invalid or expired"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "error", "Token validation failed"));
        }
    }

    // Request DTOs
    static class RegisterRequest {
        public String name;
        public String email;
        public String password;
        public String role;
    }

    static class LoginRequest {
        public String email;
        public String password;
    }
}