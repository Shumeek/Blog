package com.example.blog.repository;

import com.example.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Використовуємо для тестування JPA компонентів
@ActiveProfiles("test") // Для використання тестової конфігурації
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // Створення користувача для тестів
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
    }

    @Test
    public void testSaveUser() {
        // Збереження користувача в базу
        User savedUser = userRepository.save(testUser);

        // Перевірка, чи збережено користувача та чи отримали його ID
        assertNotNull(savedUser.getId(), "User ID should not be null after saving");
        assertEquals(testUser.getUsername(), savedUser.getUsername(), "Usernames should match");
    }

    @Test
    public void testFindByEmail() {
        // Збереження користувача
        userRepository.save(testUser);

        // Пошук користувача за email
        User foundUser = userRepository.findByEmail(testUser.getEmail()).orElseThrow();

        // Перевірка, чи знайдено користувача
        assertNotNull(foundUser, "User should be found by email");
        assertEquals(testUser.getEmail(), foundUser.getEmail(), "Emails should match");
    }

    @Test
    public void testExistsByEmail() {
        // Збереження користувача
        userRepository.save(testUser);

        // Перевірка, чи існує користувач з вказаним email
        boolean exists = userRepository.existsByEmail(testUser.getEmail());

        // Перевірка, чи метод повертає true
        assertTrue(exists, "User should exist by email");
    }

    @Test
    public void testDeleteUser() {
        // Збереження користувача
        userRepository.save(testUser);

        // Видалення користувача
        userRepository.delete(testUser);

        // Перевірка, чи користувач видалений
        assertFalse(userRepository.findById(testUser.getId()).isPresent(), "User should be deleted");
    }
}
