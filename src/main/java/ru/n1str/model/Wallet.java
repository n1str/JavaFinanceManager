package ru.n1str.model;

import java.util.*;

public class Wallet {
    private List<Transaction> transactions;
    private Map<String, Double> categoryBudgets;

    public Wallet() {
        this.transactions = new ArrayList<>();
        this.categoryBudgets = new HashMap<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void setBudget(String category, double amount) {
        categoryBudgets.put(category, amount);
    }

    public double getBudget(String category) {
        return categoryBudgets.getOrDefault(category, 0.0);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<String, Double> getCategoryBudgets() {
        return categoryBudgets;
    }

    public void setCategoryBudgets(Map<String, Double> categoryBudgets) {
        this.categoryBudgets = categoryBudgets;
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Map<String, Double> getExpensesByCategory() {
        Map<String, Double> expenses = new HashMap<>();
        transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .forEach(t -> expenses.merge(t.getCategory(), t.getAmount(), Double::sum));
        return expenses;
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpense();
    }
}
