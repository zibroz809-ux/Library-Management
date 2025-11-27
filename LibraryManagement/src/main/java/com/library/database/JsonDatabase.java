package com.library.database;

import com.google.gson.*;
import com.library.models.Loan;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonDatabase {

    private static JsonDatabase instance;
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return LocalDate.parse(json.getAsString());
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.toString());
                }
            })
            .create();

    private List<Loan> loans;

    private JsonDatabase() {
        loans = new ArrayList<>(loadList("data/loans.json", Loan[].class));
    }

    public static JsonDatabase getInstance() {
        if (instance == null) instance = new JsonDatabase();
        return instance;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void saveData() {
        saveList("data/loans.json", loans);
    }

    // membaca JSON dan mengubah ke list objek
    public static <T> List<T> loadList(String filePath, Class<T[]> clazz) {
        try {
            if (!Files.exists(Paths.get(filePath))) {
                return List.of(); // file kosong = list kosong
            }

            Reader reader = new FileReader(filePath);
            T[] array = gson.fromJson(reader, clazz);
            reader.close();

            if (array == null) return List.of();
            return Arrays.asList(array);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // menyimpan list objek ke file JSON
    public static <T> void saveList(String filePath, List<T> data) {
        try {
            Writer writer = new FileWriter(filePath);
            gson.toJson(data, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
