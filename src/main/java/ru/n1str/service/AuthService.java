package ru.n1str.service;

import ru.n1str.util.ValidationService;

public class AuthService {
    private final UserService userService;
    private final MenuService menuService;

    public AuthService(UserService userService, MenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    public void handleLogin() {
        try {
            String login = ValidationService.validateLogin(
                    menuService.readInput("Введите логин: "));

            String password = ValidationService.validatePassword(
                    menuService.readInput("Введите пароль: "));

            if (userService.login(login, password)) {
                System.out.println("✅ Успешный вход!");
            } else {
                System.out.println("❌ Неверный логин или пароль!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleRegistration() {
        try {
            String login = ValidationService.validateLogin(
                    menuService.readInput("Придумайте логин: "));

            String password = ValidationService.validatePassword(
                    menuService.readInput("Придумайте пароль: "));

            if (userService.register(login, password)) {
                System.out.println("✅ Регистрация успешна!");
            } else {
                System.out.println("❌ Пользователь с таким логином уже существует!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
