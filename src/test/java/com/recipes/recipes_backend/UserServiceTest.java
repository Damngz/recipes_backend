package com.recipes.recipes_backend;

import com.recipes.recipes_backend.model.User;
import com.recipes.recipes_backend.repository.UserRepository;
import com.recipes.recipes_backend.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
	@MockBean
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Test
	public void testGetUserByEmail_Found() {
		User mockUser = new User();
		mockUser.setEmail("test@example.com");
		mockUser.setName("Test User");
		mockUser.setPassword("hashedPassword");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

		Optional<User> result = userService.getUserByEmail("test@example.com");

		assertTrue(result.isPresent());
		assertEquals("test@example.com", result.get().getEmail());
	}

	@Test
	public void testGetUserByEmail_NotFound() {
		when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

		Optional<User> result = userService.getUserByEmail("notfound@example.com");

		assertFalse(result.isPresent());
	}

	@Test
	public void testAuthenticateUser_Success() {
		User mockUser = new User();
		mockUser.setEmail("test@example.com");
		mockUser.setPassword("$2a$10$Xh6cL8jHwv5V6ZCz3Y2KBOhKklQO3UQmh9nVnkq.WAxZ3oQpaMzPC");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

		when(passwordEncoder.matches("correct_password", mockUser.getPassword())).thenReturn(true);

		userService.authenticateUser("test@example.com", "correct_password");
	}

	@Test
	public void testAuthenticateUser_Fail_InvalidPassword() {
		User mockUser = new User();
		mockUser.setEmail("test@example.com");
		mockUser.setPassword("$2a$10$Xh6cL8jHwv5V6ZCz3Y2KBOhKklQO3UQmh9nVnkq.WAxZ3oQpaMzPC");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

		when(passwordEncoder.matches("wrong_password", mockUser.getPassword())).thenReturn(false);

		Optional<User> result = userService.authenticateUser("test@example.com", "wrong_password");

		assertFalse(result.isPresent());
	}

	@Test
	public void testAuthenticateUser_Fail_UserNotFound() {
		when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

		Optional<User> result = userService.authenticateUser("notfound@example.com", "any_password");

		assertFalse(result.isPresent());
	}
}
