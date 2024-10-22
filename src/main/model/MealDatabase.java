package model;

import java.util.ArrayList;
import java.util.List;

// Represents the collection of all meals created and stored for future use
public class MealDatabase {
    private final List<Meal> mealDatabase;

     //EFFECTS: creates meal database to store and retrieve meals
    public MealDatabase() {
        this.mealDatabase = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if new meal, stores in database
    //          if not new, does nothing
    public Boolean storeEntry(Meal meal) {
        if (!mealDatabase.contains(meal)) {
            mealDatabase.add(meal);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: searches for meal by index and returns in
    public Meal getMealByIndex(int index) {
        return mealDatabase.get(index);
    }

    // EFFECTS: returns true if database is empty, false otherwise
    public Boolean isDatabaseEmpty() {
        return mealDatabase.isEmpty();
    }

    public List<Meal> getMealDatabase() {
        return mealDatabase;
    }

}
