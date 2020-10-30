package persistence;

import model.Log;
import model.Meal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that can read a saved meal log from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read data from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Log from file and returns it
    // throws IOException if an error occurs reading data from file
    public Log read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLog(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // parses log from JSON object and returns in
    private Log parseLog(JSONObject jsonObject) {
        Log log = new Log();
        addMeals(log, jsonObject);
        return log;
    }

    // MODIFIES: log
    // EFFECTS: parses meals from JSON object and adds them to log
    private void addMeals(Log log, JSONObject jsonObject) {
        JSONArray jsonArrayLog = jsonObject.getJSONArray("logMeals");
        JSONArray jsonArrayDB = jsonObject.getJSONArray("dbMeals");
        for (Object json : jsonArrayLog) {
            JSONObject nextMeal = (JSONObject) json;
            Meal meal = returnJsonMeal(nextMeal);
            log.addMealToLog(meal);
        }

        for (Object json: jsonArrayDB) {
            JSONObject nextMeal = (JSONObject) json;
            Meal meal = returnJsonMeal(nextMeal);
            log.storeMealInDatabase(meal);
        }

    }

    // EFFECTS: parses meal from JSON object and returns it
    private Meal returnJsonMeal(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int cals = jsonObject.getInt("cals");
        JSONArray ingredients = jsonObject.getJSONArray("ingredients");

        Meal meal = new Meal(name, cals);

        for (Object json : ingredients) {
            meal.addIngredient(json.toString());
        }
        return meal;
    }

}
