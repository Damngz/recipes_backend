package com.recipes.recipes_backend.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expirationTime;

  public String generateToken(String email, String role) {
    return JWT.create()
      .withSubject(email)
      .withClaim("role", role)
      .withIssuedAt(new Date())
      .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
      .sign(Algorithm.HMAC256(secret));
    }

    public String extractEmail(String token) {
      return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String token, String email) {
      String extractedEmail = extractEmail(token);
      return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
      Date expiration = JWT.decode(token).getExpiresAt();
      return expiration.before(new Date());
    }
}
