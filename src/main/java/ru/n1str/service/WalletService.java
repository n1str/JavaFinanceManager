package ru.n1str.service;

import ru.n1str.model.*;
import java.util.Map;
import java.util.List;

public class WalletService {
    private final UserService userService;

    public WalletService(UserService userService) {
        this.userService = userService;
    }

    public boolean addIncome(double amount, String category) {
        if (!userService.isLoggedIn() || amount <= 0) {
            return false;
        }

        Transaction transaction = new Transaction(amount, category, TransactionType.INCOME);
        userService.getCurrentUser().getWallet().addTransaction(transaction);
        System.out.println("✅ Доход успешно добавлен: " + transaction);
        return true;
    }

    public boolean addExpense(double amount, String category) {
        if (!userService.isLoggedIn() || amount <= 0) {
            return false;
        }

        Wallet wallet = userService.getCurrentUser().getWallet();
        Transaction transaction = new Transaction(amount, category, TransactionType.EXPENSE);

        Map<String, Double> expenses = wallet.getExpensesByCategory();
        double currentCategoryExpense = expenses.getOrDefault(category, 0.0) + amount;
        double categoryBudget = wallet.getBudget(category);

        if (categoryBudget > 0 && currentCategoryExpense > categoryBudget) {
            System.out.println("⚠️ ВНИМАНИЕ! Превышен бюджет по категории " + category);
            System.out.printf("Бюджет: %.2f руб., Текущие расходы: %.2f руб.\n",
                    categoryBudget, currentCategoryExpense);
        }

        if (wallet.getTotalIncome() < wallet.getTotalExpense() + amount) {
            System.out.println("⚠️ ВНИМАНИЕ! Расходы превышают доходы!");
        }

        wallet.addTransaction(transaction);
        System.out.println("✅ Расход успешно добавлен: " + transaction);
        return true;
    }

    public boolean setBudget(String category, double amount) {
        if (!userService.isLoggedIn() || amount <= 0) {
            return false;
        }

        userService.getCurrentUser().getWallet().setBudget(category, amount);
        System.out.printf("✅ Установлен бюджет для категории %s: %.2f руб.\n",
                category, amount);
        return true;
    }

    public void showTransactions() {
        if (!userService.isLoggedIn()) {
            return;
        }

        List<Transaction> transactions = userService.getCurrentUser().getWallet().getTransactions();
        System.out.println("\n=== Список транзакций ===");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, transactions.get(i));
        }
    }

    public boolean editTransaction(int index, double amount, String category) {
        if (!userService.isLoggedIn() || amount <= 0) {
            return false;
        }

        List<Transaction> transactions = userService.getCurrentUser().getWallet().getTransactions();
        if (index < 0 || index >= transactions.size()) {
            System.out.println("❌ Неверный индекс транзакции!");
            return false;
        }

        Transaction transaction = transactions.get(index);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        System.out.println("✅ Транзакция успешно отредактирована: " + transaction);
        return true;
    }

    public boolean deleteTransaction(int index) {
        if (!userService.isLoggedIn()) {
            return false;
        }

        List<Transaction> transactions = userService.getCurrentUser().getWallet().getTransactions();
        if (index < 0 || index >= transactions.size()) {
            System.out.println("❌ Неверный индекс транзакции!");
            return false;
        }

        Transaction removed = transactions.remove(index);
        System.out.println("✅ Транзакция успешно удалена: " + removed);
        return true;
    }

    public void showStatistics() {
        if (!userService.isLoggedIn()) {
            return;
        }

        Wallet wallet = userService.getCurrentUser().getWallet();
        double totalIncome = wallet.getTotalIncome();
        double totalExpense = wallet.getTotalExpense();

        System.out.println("\n=== Финансовая статистика ===");
        System.out.printf("💰 Общий доход: %.2f руб.\n", totalIncome);
        System.out.printf("💸 Общий расход: %.2f руб.\n", totalExpense);
        System.out.printf("💵 Текущий баланс: %.2f руб.\n", totalIncome - totalExpense);

        System.out.println("\n📊 Расходы по категориям:");
        Map<String, Double> expensesByCategory = wallet.getExpensesByCategory();
        Map<String, Double> budgets = wallet.getCategoryBudgets();

        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            String category = entry.getKey();
            double expense = entry.getValue();
            double budget = budgets.getOrDefault(category, 0.0);

            System.out.printf("- %s: %.2f руб.", category, expense);
            if (budget > 0) {
                double remaining = budget - expense;
                System.out.printf(" (Бюджет: %.2f руб., Остаток: %.2f руб.)",
                        budget, remaining);
                if (remaining < 0) {
                    System.out.print(" ⚠️");
                }
            }
            System.out.println();
        }
    }
}
