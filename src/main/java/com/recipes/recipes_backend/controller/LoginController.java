package com.recipes.recipes_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipes.recipes_backend.model.ApiResponse;
import com.recipes.recipes_backend.model.Login;
import com.recipes.recipes_backend.model.User;
import com.recipes.recipes_backend.security.JwtUtil;
import com.recipes.recipes_backend.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {
  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping
  public ResponseEntity<ApiResponse<String>> login(@RequestBody Login loginRequest) {
    log.info(loginRequest.toString());
    log.info("eldiablo");
    try {
      Optional<User> user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
      
      if (user.isPresent()) {
        String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getRole());
        ApiResponse<String> response = new ApiResponse<>(200, "Login successful", token);
        return ResponseEntity.ok(response);
      } else {
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body(new ApiResponse<>(401, "Invalid credentials", null));
      }
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(new ApiResponse<>(401, "Invalid credentials", null));
    }
  }
}
