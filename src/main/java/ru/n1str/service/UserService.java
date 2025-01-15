package ru.n1str.service;

import ru.n1str.model.User;
import ru.n1str.util.FileManager;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users;
    private User currentUser;

    public UserService() {
        this.users = new HashMap<>();
        loadData();
    }

    public boolean register(String login, String password) {
        if (users.containsKey(login)) {
            return false;
        }
        users.put(login, new User(login, password));
        saveData();
        return true;
    }

    public boolean login(String login, String password) {
        User user = users.get(login);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        saveData();
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

    private void loadData() {
        Map<String, User> loadedUsers = FileManager.loadData();
        if (loadedUsers != null) {
            users = loadedUsers;
        }
    }

    public void saveData() {
        FileManager.saveData(users);
    }
}
