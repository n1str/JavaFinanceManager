package ru.n1str.model;

import java.time.LocalDateTime;

public class Transaction {
    private double amount;
    private String category;
    private TransactionType type;
    private LocalDateTime dateTime;

    public Transaction(double amount, String category, TransactionType type) {
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f руб. (Категория: %s, Дата: %s)",
                type == TransactionType.INCOME ? "Доход" : "Расход",
                amount,
                category,
                dateTime.toString());
    }
}
