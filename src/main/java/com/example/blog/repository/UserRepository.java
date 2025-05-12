package com.example.blog.repository;

import com.example.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // Додаємо метод для пошуку користувача за email
    boolean existsByEmail(String email);  // Метод для перевірки наявності користувача з таким email
}
