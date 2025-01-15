package ru.n1str.service;

import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printWelcome() {
        System.out.println("=== Приложение для управления финансами ===");
    }

    public void printAuthMenu() {
        System.out.println("\n1. Войти");
        System.out.println("2. Зарегистрироваться");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    public void printMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Добавить доход");
        System.out.println("2. Добавить расход");
        System.out.println("3. Установить бюджет категории");
        System.out.println("4. Просмотреть статистику");
        System.out.println("5. Редактировать транзакцию");
        System.out.println("6. Удалить транзакцию");
        System.out.println("7. Выйти из аккаунта");
        System.out.println("0. Завершить работу");
        System.out.print("Выберите действие: ");
    }

    public String readChoice() {
        return scanner.nextLine();
    }

    public String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
