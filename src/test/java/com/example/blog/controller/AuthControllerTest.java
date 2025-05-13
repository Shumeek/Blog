package com.example.blog.controller;

import com.example.blog.service.UserService;
import com.example.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
    }

    @Test
    public void testRegisterUserEmailAlreadyRegistered() {
        // Мокування поведінки UserService для існуючого email
        when(userService.isEmailAlreadyRegistered(testUser.getEmail())).thenReturn(true);

        String result = authController.registerUser(testUser.getUsername(), testUser.getEmail(), testUser.getPassword(), model);

        // Перевірка, що контролер повертає правильний шаблон при наявності помилки
        assertEquals("register", result);
        verify(model).addAttribute("emailError", "Цей email вже зареєстровано");
    }

    @Test
    public void testRegisterUserInvalidEmail() {
        // Мокування поведінки для неправильного формату email
        when(userService.isEmailAlreadyRegistered(testUser.getEmail())).thenReturn(false);
        String invalidEmail = "invalidemail";
        String result = authController.registerUser(testUser.getUsername(), invalidEmail, testUser.getPassword(), model);

        // Перевірка, що контролер повертає правильний шаблон при неправильному email
        assertEquals("register", result);
        verify(model).addAttribute("emailError", "Невірний формат email");
    }

    @Test
    public void testRegisterUserShortPassword() {
        // Мокування поведінки для короткого паролю
        when(userService.isEmailAlreadyRegistered(testUser.getEmail())).thenReturn(false);
        String result = authController.registerUser(testUser.getUsername(), testUser.getEmail(), "12345", model);

        // Перевірка, що контролер повертає правильний шаблон при короткому паролі
        assertEquals("register", result);
        verify(model).addAttribute("passwordError", "Пароль повинен містити не менше 6 символів");
    }

    @Test
    public void testLoginUserIncorrectCredentials() {
        // Мокування поведінки для невірного логіну або паролю
        when(userService.authenticateUser(testUser.getEmail(), testUser.getPassword())).thenReturn(false);

        String result = authController.loginUser(testUser.getEmail(), testUser.getPassword(), model);

        // Перевірка, що контролер повертає правильний шаблон при неправильному логіні/паролі
        assertEquals("login", result);
        verify(model).addAttribute("error", "Невірний логін або пароль");
    }

    @Test
    public void testResetPasswordPasswordsDoNotMatch() {
        String result = authController.resetPassword(testUser.getEmail(), "newPassword123", "differentPassword123", model);

        // Перевірка, що контролер повертає правильний шаблон при непарних паролях
        assertEquals("restore-password", result);
        verify(model).addAttribute("error", "Паролі не співпадають");
    }

    @Test
    public void testResetPasswordShortPassword() {
        String result = authController.resetPassword(testUser.getEmail(), "short", "short", model);

        // Перевірка, що контролер повертає правильний шаблон при короткому паролі
        assertEquals("restore-password", result);
        verify(model).addAttribute("error", "Пароль повинен містити не менше 6 символів");
    }

    @Test
    public void testResetPasswordSuccess() {
        // Мокування успішного скидання паролю
        when(userService.resetPassword(testUser.getEmail(), "newPassword123")).thenReturn(true);

        String result = authController.resetPassword(testUser.getEmail(), "newPassword123", "newPassword123", model);

        // Перевірка, що після успішного скидання паролю відбудеться перенаправлення
        assertEquals("redirect:/auth/login?passwordChanged", result);
    }
}
