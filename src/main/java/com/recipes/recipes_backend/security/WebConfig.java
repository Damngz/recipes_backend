package com.recipes.recipes_backend.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/login")
      .allowedOrigins("http://localhost:8080")
      .allowedMethods("POST")
      .allowedHeaders("*")
      .allowCredentials(true);
  }
}