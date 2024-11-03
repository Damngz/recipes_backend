package com.recipes.recipes_backend.repository;

import org.springframework.stereotype.Repository;
import com.recipes.recipes_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByEmailAndPassword(String email, String password);
}
