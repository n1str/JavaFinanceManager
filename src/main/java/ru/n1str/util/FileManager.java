package ru.n1str.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import ru.n1str.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private static final String DATA_FILE = "users_data.json";
    private static final Type USER_MAP_TYPE = new TypeToken<Map<String, User>>(){}.getType();
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    private static boolean checkDataFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    try (Writer writer = new FileWriter(file)) {
                        gson.toJson(new HashMap<String, User>(), USER_MAP_TYPE, writer);
                    }
                    System.out.println("✨ Создан новый файл данных: " + DATA_FILE);
                    return true;
                }
                return false;
            } catch (IOException e) {
                System.err.println("❌ Ошибка при создании файла данных: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static void saveData(Map<String, User> users) {
        if (!checkDataFile()) {
            return;
        }

        try (Writer writer = new FileWriter(DATA_FILE)) {
            gson.toJson(users, USER_MAP_TYPE, writer);
        } catch (IOException e) {
            System.err.println("❌ Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public static Map<String, User> loadData() {
        if (!checkDataFile()) {
            return null;
        }

        try (Reader reader = new FileReader(DATA_FILE)) {
            return gson.fromJson(reader, USER_MAP_TYPE);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("❌ Ошибка при загрузке данных: " + e.getMessage());
            return null;
        }
    }
}
