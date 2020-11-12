package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Represents a single log with the day's date, meal entries list, and total calories eaten
public class Log implements Writable {
    private final String fullDate;
    private final List<Meal> todayMealLog;
    private final MealDatabase mdbObject;

    private int totalCalories;

    //EFFECTS: creates today's meal log with current date
    //         initializes meal database and tracks calories
    public Log() {
        LocalDateTime date = LocalDateTime.now();
        String month = date.getMonth().name();
        String dayOfWeek = date.getDayOfWeek().name();
        int dayNumber = date.getDayOfMonth();
        this.fullDate = dayOfWeek + " " + month + " " + dayNumber;

        this.todayMealLog = new ArrayList<>();
        this.totalCalories = 0;
        this.mdbObject = new MealDatabase();
    }

    // MODIFIES: this, MealDatabase
    // EFFECTS: adds meal to today's log and to meal database if new
    //          updates total calories
    public void addMealToLogAndDatabase(Meal meal) {
        addMealToLog(meal);
        storeMealInDatabase(meal);
    }

    // MODIFIES: this, MealDatabase
    // EFFECTS: adds meal to today's log and to meal database if new
    //          updates total calories
    public void addMealToLog(Meal meal) {
        this.todayMealLog.add(meal);
        totalCalories += meal.getCals();
    }

    // MODIFIES: this, MealDatabase
    // EFFECTS: adds meal to database only
    public void storeMealInDatabase(Meal meal) {
        mdbObject.storeEntry(meal);
    }

    // MODIFIES: this
    // EFFECTS: if meal in log, removes it and deducts calories, returns true
    //          returns false if meal not in log
    public Boolean removeMealFromLog(Meal meal) {
        if (todayMealLog.contains(meal)) {
            this.todayMealLog.remove(meal);
            totalCalories -= meal.getCals();
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if meal index in range, removes meal and deducts calories, returns true
    //          returns false if index not range
    public Boolean removeMealFromLog(int index) {
        if (!(index >= todayMealLog.size())) {
            int mealCals = todayMealLog.get(index).getCals();
            this.todayMealLog.remove(index);
            totalCalories -= mealCals;
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if meal log is empty, false otherwise
    public Boolean isLogEmpty() {
        return todayMealLog.isEmpty();
    }

    public List<Meal> getMealLog() {
        return todayMealLog;
    }

    public MealDatabase getMealDatabaseObject() {
        return mdbObject;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public String getFullDate() {
        return fullDate;
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: converts meal data in log and database to JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", getFullDate());
        json.put("logMeals", mealsToJson(todayMealLog));
        json.put("dbMeals", mealsToJson(mdbObject.getMealDatabase()));
        return json;
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns meals in the given list as a JSON array
    private JSONArray mealsToJson(List<Meal> mealList) {
        JSONArray jsonArray = new JSONArray();
        for (Meal meal :  mealList) {
            jsonArray.put(meal.toJson());
        }
        return jsonArray;
    }
}
