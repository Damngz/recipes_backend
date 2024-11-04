package com.recipes.recipes_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recipes.recipes_backend.model.User;
import com.recipes.recipes_backend.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Optional<User> authenticateUser(String email, String rawPassword) {
    return userRepository
      .findByEmail(email)
      .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
  }
}
