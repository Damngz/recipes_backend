package com.recipes.recipes_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.recipes_backend.model.Login;
import com.recipes.recipes_backend.model.User;
import com.recipes.recipes_backend.security.JwtUtil;
import com.recipes.recipes_backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtUtil jwtUtil;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testLogin_Success() throws Exception {
    User mockUser = new User();
    mockUser.setEmail("test@example.com");
    mockUser.setPassword("hashedPassword");
    mockUser.setRole("USER");

    when(userService.authenticateUser("test@example.com", "correct_password"))
      .thenReturn(java.util.Optional.of(mockUser));

    when(jwtUtil.generateToken("test@example.com", "USER")).thenReturn("fake-jwt-token");

    Login loginRequest = new Login();
    loginRequest.setEmail("test@example.com");
    loginRequest.setPassword("correct_password");

    mockMvc.perform(post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(loginRequest)))
      .andExpect(status().isOk())
      .andExpect(content().string("fake-jwt-token"));
  }

  @Test
  public void testLogin_Fail_InvalidCredentials() throws Exception {
    when(userService.authenticateUser("test@example.com", "wrong_password"))
      .thenReturn(java.util.Optional.empty());

    Login loginRequest = new Login();
    loginRequest.setEmail("test@example.com");
    loginRequest.setPassword("wrong_password");

    mockMvc.perform(post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(loginRequest)))
      .andExpect(status().isUnauthorized())
      .andExpect(content().string("Invalid credentials"));
  }

  @Test
  public void testLogin_Fail_Exception() throws Exception {
    when(userService.authenticateUser("test@example.com", "correct_password"))
      .thenThrow(new RuntimeException("Unexpected error"));

    Login loginRequest = new Login();
    loginRequest.setEmail("test@example.com");
    loginRequest.setPassword("correct_password");

    mockMvc.perform(post("/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(loginRequest)))
      .andExpect(status().isUnauthorized())
      .andExpect(content().string("Invalid credentials"));
  }
}
