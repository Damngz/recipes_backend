package com.recipes.recipes_backend;

import org.junit.jupiter.api.Test;

import com.recipes.recipes_backend.model.Login;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
  @Test
  public void testLoginGettersAndSetters() {
    Login login = new Login();
    
    login.setEmail("test@example.com");
    login.setPassword("pass");

    assertEquals("test@example.com", login.getEmail());
    assertEquals("pass", login.getPassword());
  }
}
