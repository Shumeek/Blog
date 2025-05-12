package com.example.blog.service;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Реєстрація користувача
    public User registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));  // Пароль шифрується перед збереженням
        return userRepository.save(user);  // Збереження користувача в базі даних
    }

    // Пошук користувача за email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Перевірка наявності email в базі
    public boolean isEmailAlreadyRegistered(String email) {
        return userRepository.existsByEmail(email);  // Перевірка на існування email
    }

    // Скидання паролю
    public boolean resetPassword(String email, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));  // Шифрування нового пароля
            userRepository.save(user);  // Збереження нового пароля в базі даних
            return true;  // Повертаємо true, якщо пароль успішно змінений
        } else {
            return false;  // Повертаємо false, якщо користувача не знайдено
        }
    }

    // Перевірка логіну та паролю
    public boolean authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(password, user.getPassword());  // Перевірка пароля
        }
        return false;  // Якщо користувач не знайдений
    }

}
