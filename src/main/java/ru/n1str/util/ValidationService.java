package ru.n1str.util;

public class ValidationService {

    public static String validateLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Логин не может быть пустым!");
        }
        if (login.length() < 3) {
            throw new IllegalArgumentException("❌ Логин должен содержать минимум 3 символа!");
        }
        if (login.length() > 20) {
            throw new IllegalArgumentException("❌ Логин не может быть длиннее 20 символов!");
        }
        if (!login.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("❌ Логин может содержать только буквы, цифры и знак подчеркивания!");
        }
        return login.trim();
    }

    public static String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Пароль не может быть пустым!");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("❌ Пароль должен содержать минимум 4 символа!");
        }
        if (password.length() > 30) {
            throw new IllegalArgumentException("❌ Пароль не может быть длиннее 30 символов!");
        }
        return password;
    }

    public static double validateAmount(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Сумма не может быть пустой!");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("❌ Некорректный формат суммы! Используйте числа и точку/запятую для копеек.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("❌ Сумма должна быть больше нуля!");
        }
        if (amount > 999999999) {
            throw new IllegalArgumentException("❌ Слишком большая сумма! Максимум 999,999,999");
        }

        return amount;
    }

    public static String validateCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Категория не может быть пустой!");
        }
        if (category.length() > 30) {
            throw new IllegalArgumentException("❌ Название категории слишком длинное! Максимум 30 символов.");
        }
        if (!category.matches("^[a-zA-Zа-яА-Я0-9\\s_-]+$")) {
            throw new IllegalArgumentException("❌ Категория может содержать только буквы, цифры, пробелы, тире и знак подчеркивания!");
        }
        return category.trim();
    }

    public static int validateListIndex(String indexStr, int maxIndex) {
        if (indexStr == null || indexStr.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Номер не может быть пустым!");
        }

        int index;
        try {
            index = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("❌ Некорректный формат номера! Используйте целые числа.");
        }

        if (index < 1 || index > maxIndex) {
            throw new IllegalArgumentException("❌ Некорректный номер! Введите число от 1 до " + maxIndex);
        }

        return index;
    }
}
