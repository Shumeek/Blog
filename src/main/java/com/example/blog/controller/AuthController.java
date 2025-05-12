package com.example.blog.controller;

import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.blog.entity.User;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Показати форму реєстрації
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());  // Якщо ви маєте клас User, де зберігається email, пароль, то передаєте його в модель
        return "register";  // Повертає шаблон register.html
    }

    // Показати форму логіну
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Повертає шаблон login.html

    }

    // Обробка реєстрації користувача
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {

        // Перевірка, чи вже зареєстрований користувач
        if (userService.isEmailAlreadyRegistered(email)) {
            model.addAttribute("emailError", "Цей email вже зареєстровано");
            return "register";  // Повертає користувача на форму реєстрації з помилкою
        }

        // Перевірка email
        if (!isValidEmail(email)) {
            model.addAttribute("emailError", "Невірний формат email");
            return "register";  // Якщо email невірний, показуємо форму реєстрації з помилкою
        }

        // Перевірка пароля
        if (password.length() < 6) {
            model.addAttribute("passwordError", "Пароль повинен містити не менше 6 символів");
            return "register";  // Якщо пароль занадто короткий, показуємо форму реєстрації з помилкою
        }

        // Реєстрація користувача
        userService.registerUser(username, email, password);
        return "redirect:/auth/login";  // Перенаправлення на сторінку логіну після успішної реєстрації
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model) {
        // Перевірка правильності логіну та пароля
        if (!userService.authenticateUser(email, password)) {
            model.addAttribute("error", "Невірний логін або пароль");

            return "login";  // Повертаємо на сторінку логіну з помилкою
        }

        // Якщо логін успішний, можна зробити перенаправлення
        return "redirect:/dashboard";  // Приклад перенаправлення на сторінку після успішного входу
    }


    // Показати форму для скидання паролю
    @GetMapping("/restore-password")
    public String showRestorePasswordForm() {
        return "restore-password";  // Повертає шаблон restore-password.html
    }

    // Обробка запиту на скидання паролю
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {
        // Перевірка на збіг паролів
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Паролі не співпадають");
            return "restore-password";  // Повертає користувача на форму для скидання паролю з помилкою
        }

        // Перевірка мінімальної довжини паролю
        if (newPassword.length() < 6) {
            model.addAttribute("error", "Пароль повинен містити не менше 6 символів");
            return "restore-password";  // Повертає користувача на форму для скидання паролю з помилкою
        }

        // Скидання паролю
        boolean success = userService.resetPassword(email, newPassword);
        if (success) {
            return "redirect:/auth/login?passwordChanged";  // Перенаправлення на сторінку входу після зміни паролю
        } else {
            return "redirect:/auth/restore-password?error";  // Якщо email не знайдено
        }
    }

    // Допоміжний метод для перевірки формату email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
