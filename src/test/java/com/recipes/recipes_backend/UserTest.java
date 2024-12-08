package com.recipes.recipes_backend;

import org.junit.jupiter.api.Test;

import com.recipes.recipes_backend.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
  @Test
  public void testUserGettersAndSetters() {

    User user = new User();

    user.setEmail("test@example.com");
    user.setName("Test User");
    user.setPassword("hashedPassword");
    user.setRole("user");
    user.setUserId((long) 1);

    assertEquals("test@example.com", user.getEmail());
    assertEquals("Test User", user.getName());
    assertEquals("user", user.getRole());
    assertEquals((long) 1, user.getUserId());
    assertEquals("hashedPassword", user.getPassword());
  }
}

