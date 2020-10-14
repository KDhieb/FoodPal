package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Represents a single log with the day's date, meal entries list, and total calories eaten
public class Log {
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
    public void addMealToLog(Meal meal) {
        this.todayMealLog.add(meal);
        mdbObject.storeEntry(meal);
        totalCalories += meal.getCals();
    }

    // REQUIRES:
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


}
