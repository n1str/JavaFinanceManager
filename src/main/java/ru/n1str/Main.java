package ru.n1str;

import ru.n1str.service.*;
import java.util.Scanner;

public class Main {
    private final UserService userService;
    private final WalletService walletService;
    private final MenuService menuService;
    private final AuthService authService;
    private final TransactionService transactionService;
    private final Scanner scanner;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
        this.walletService = new WalletService(userService);
        this.menuService = new MenuService(scanner);
        this.authService = new AuthService(userService, menuService);
        this.transactionService = new TransactionService(walletService, menuService, userService);
    }

    public void run() {
        boolean running = true;
        menuService.printWelcome();

        while (running) {
            if (!userService.isLoggedIn()) {
                running = handleAuthMenu();
            } else {
                running = handleMainMenu();
            }
        }

        scanner.close();
        userService.saveData();
    }

    private boolean handleAuthMenu() {
        menuService.printAuthMenu();
        String choice = menuService.readChoice();

        switch (choice) {
            case "1":
                authService.handleLogin();
                return true;
            case "2":
                authService.handleRegistration();
                return true;
            case "0":
                return false;
            default:
                System.out.println("❌ Неверный выбор!");
                return true;
        }
    }

    private boolean handleMainMenu() {
        menuService.printMainMenu();
        String choice = menuService.readChoice();

        switch (choice) {
            case "1":
                transactionService.handleAddIncome();
                return true;
            case "2":
                transactionService.handleAddExpense();
                return true;
            case "3":
                transactionService.handleSetBudget();
                return true;
            case "4":
                transactionService.handleShowStatistics();
                return true;
            case "5":
                transactionService.handleEditTransaction();
                return true;
            case "6":
                transactionService.handleDeleteTransaction();
                return true;
            case "7":
                userService.logout();
                return true;
            case "0":
                return false;
            default:
                System.out.println("❌ Неверный выбор!");
                return true;
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
