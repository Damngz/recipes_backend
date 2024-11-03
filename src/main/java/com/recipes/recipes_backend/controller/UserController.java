package com.recipes.recipes_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipes.recipes_backend.model.ApiResponse;
import com.recipes.recipes_backend.model.User;
import com.recipes.recipes_backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
    User createdUser = userService.createUser(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    ApiResponse<User> response = new ApiResponse<>(201, "User created successfully", createdUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
