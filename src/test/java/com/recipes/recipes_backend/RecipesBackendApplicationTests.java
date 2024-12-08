package com.recipes.recipes_backend;

import com.recipes.recipes_backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipesBackendApplicationTests {
	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		assertNotNull(userService, "El servicio UserService debería estar presente en el contexto");
	}

	@Test
	void testMainMethod() {
		RecipesBackendApplication.main(new String[] {});
	}
}
