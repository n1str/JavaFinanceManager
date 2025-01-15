package ru.n1str.service;

import ru.n1str.util.ValidationService;

public class TransactionService {
    private final WalletService walletService;
    private final MenuService menuService;
    private final UserService userService;

    public TransactionService(WalletService walletService, MenuService menuService, UserService userService) {
        this.walletService = walletService;
        this.menuService = menuService;
        this.userService = userService;
    }

    public void handleAddIncome() {
        try {
            double amount = ValidationService.validateAmount(
                    menuService.readInput("Введите сумму дохода: "));

            String category = ValidationService.validateCategory(
                    menuService.readInput("Введите категорию дохода: "));

            walletService.addIncome(amount, category);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleAddExpense() {
        try {
            double amount = ValidationService.validateAmount(
                    menuService.readInput("Введите сумму расхода: "));

            String category = ValidationService.validateCategory(
                    menuService.readInput("Введите категорию расхода: "));

            walletService.addExpense(amount, category);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleSetBudget() {
        try {
            String category = ValidationService.validateCategory(
                    menuService.readInput("Введите категорию: "));

            double amount = ValidationService.validateAmount(
                    menuService.readInput("Введите сумму бюджета: "));

            walletService.setBudget(category, amount);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleEditTransaction() {
        walletService.showTransactions();
        try {
            int index = ValidationService.validateListIndex(
                    menuService.readInput("Введите номер транзакции для редактирования: "),
                    userService.getCurrentUser().getWallet().getTransactions().size());

            double amount = ValidationService.validateAmount(
                    menuService.readInput("Введите новую сумму: "));

            String category = ValidationService.validateCategory(
                    menuService.readInput("Введите новую категорию: "));

            walletService.editTransaction(index - 1, amount, category);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleDeleteTransaction() {
        walletService.showTransactions();
        try {
            int index = ValidationService.validateListIndex(
                    menuService.readInput("Введите номер транзакции для удаления: "),
                    userService.getCurrentUser().getWallet().getTransactions().size());

            walletService.deleteTransaction(index - 1);
            System.out.println("✅ Транзакция удалена!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleShowStatistics() {
        walletService.showStatistics();
    }
}
