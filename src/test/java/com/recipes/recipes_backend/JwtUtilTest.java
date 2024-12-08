package com.recipes.recipes_backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.recipes.recipes_backend.security.JwtUtil;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@WebMvcTest(JwtUtil.class)
class JwtUtilTest {

  private JwtUtil jwtUtil;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expirationTime;

  @BeforeEach
  void setUp() {
    jwtUtil = new JwtUtil();
    jwtUtil.setSecret(secret);
    jwtUtil.setExpirationTime(expirationTime);
  }

  @Test
  void testGenerateToken() {
    String email = "usuario@example.com";
    String role = "ROLE_USER";
    String token = jwtUtil.generateToken(email, role);

    assertNotNull(token);
  }

  @Test
  void testExtractEmail() {
    String email = "usuario@example.com";
    String token = jwtUtil.generateToken(email, "ROLE_USER");
    String extractedEmail = jwtUtil.extractEmail(token);

    assertEquals(email, extractedEmail);
  }

  @Test
  void testValidateToken_Valid() {
    String email = "usuario@example.com";
    String role = "ROLE_USER";
    String token = jwtUtil.generateToken(email, role);
    boolean isValid = jwtUtil.validateToken(token, email);

    assertTrue(isValid);
  }

  @Test
  void testValidateToken_Invalid() {
    String email = "usuario@example.com";
    String role = "ROLE_USER";
    String token = jwtUtil.generateToken(email, role);
    boolean isValid = jwtUtil.validateToken(token, "otroEmail@example.com");

    assertFalse(isValid);
  }

  @Test
  void testIsTokenExpired() {
    String email = "usuario@example.com";
    String role = "ROLE_USER";
    String token = jwtUtil.generateToken(email, role);
    boolean isExpired = jwtUtil.isTokenExpired(token);

    assertFalse(isExpired);
  }
}
