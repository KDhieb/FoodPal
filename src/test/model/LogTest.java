package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exceptions.InvalidInputException;

import static org.junit.jupiter.api.Assertions.*;

public class LogTest {
    Log today;
    Meal mealOne;
    Meal mealTwo;
    int mealOneCalories;
    int mealTwoCalories;
    int calorieBalance;
    int logCalories;
    List<Meal> log;

    @BeforeEach
    public void runBefore() throws InvalidInputException {
        today = new Log();
        mealOne = new Meal("Burrito", 350);
        mealTwo = new Meal("Salad", 200);
        mealOneCalories = mealOne.getCals();
        mealTwoCalories = mealTwo.getCals();
        calorieBalance = 0;
        logCalories = today.getTotalCalories();
        log = today.getMealLog();
    }

    @Test
    public void testAddMealToLogOneMeal() {
        today.addMealToLogAndDatabase(mealOne);
        updateLogCalories();

        assertEquals(mealOneCalories, logCalories);
        assertEquals(1, log.size());
    }

    @Test
    public void testAddMealToLogTwoDifferentMeal() {
        today.addMealToLogAndDatabase(mealOne);
        today.addMealToLogAndDatabase(mealTwo);

        int calorieBalance = mealOneCalories + mealTwoCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
        assertEquals(2, log.size());
    }


    @Test
    public void testAddMealToLogTwoSameMeals() {
        today.addMealToLogAndDatabase(mealOne);
        today.addMealToLogAndDatabase(mealOne);

        calorieBalance = mealOneCalories * 2;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
        assertEquals(2, log.size());
    }

    @Test
    public void testAddMealToLogMealOneTwoOne() {
        today.addMealToLogAndDatabase(mealOne);
        today.addMealToLogAndDatabase(mealTwo);
        today.addMealToLogAndDatabase(mealOne);

        calorieBalance = (mealOneCalories * 2) + mealTwoCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
        assertEquals(3, log.size());
    }

    @Test
    public void testRemoveMealFromLogEmpty() {
        assertFalse(today.removeMealFromLog(mealOne));
    }

    @Test
    public void testRemoveMealFromLogOneMeal() {
        today.addMealToLogAndDatabase(mealOne);
        updateLogCalories();
        assertEquals(mealOneCalories, logCalories);
        assertTrue(today.removeMealFromLog(mealOne));
        updateLogCalories();
        assertEquals(0, logCalories);
    }

    @Test
    public void testRemoveMealFromLogTwoDifferentMeals() {
        today.addMealToLogAndDatabase(mealOne);
        today.addMealToLogAndDatabase(mealTwo);

        calorieBalance = mealOneCalories + mealTwoCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
        assertTrue(today.removeMealFromLog(mealOne));
        calorieBalance -= mealOneCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);

        assertTrue(today.removeMealFromLog(mealTwo));
        calorieBalance -= mealTwoCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
    }

    @Test
    public void testRemoveMealFromLogTwoSameMeals() {
        today.addMealToLogAndDatabase(mealOne);
        today.addMealToLogAndDatabase(mealOne);

        calorieBalance = mealOneCalories * 2;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);

        assertTrue(today.removeMealFromLog(mealOne));
        calorieBalance -= mealOneCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);

        assertTrue(today.removeMealFromLog(mealOne));
        calorieBalance -= mealOneCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
    }

    @Test
    public void testRemoveMealFromLogTwoSameMealsOneTwoOne() {
        today.addMealToLogAndDatabase(mealOne);
        today.addMealToLogAndDatabase(mealTwo);
        today.addMealToLogAndDatabase(mealOne);

        assertEquals(3, log.size());
        calorieBalance = (mealOneCalories * 2) + mealTwoCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);

        assertTrue(today.removeMealFromLog(mealOne));
        assertEquals(2, log.size());
        calorieBalance -= mealOneCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);

        assertTrue(today.removeMealFromLog(mealTwo));
        assertEquals(1, log.size());
        calorieBalance -= mealTwoCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);

        assertTrue(today.removeMealFromLog(mealOne));
        assertEquals(0, log.size());
        calorieBalance -= mealOneCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
    }

    @Test
    public void testRemoveMealUsingIndex() {
        today.addMealToLog(mealOne);
        today.addMealToLog(mealTwo);

        assertEquals(mealTwoCalories + mealOneCalories, today.getTotalCalories());
        assertEquals(2, log.size());

        assertTrue(today.removeMealFromLog(1));
        assertEquals(1, log.size());
        assertEquals(mealOneCalories, today.getTotalCalories());

        assertTrue(today.removeMealFromLog(0));
        assertEquals(0, log.size());
        assertEquals(0, today.getTotalCalories());
    }

    @Test
    public void testRemoveMealUsingIndexFail() {
        today.addMealToLog(mealOne);
        today.addMealToLog(mealTwo);
        assertFalse(today.removeMealFromLog(2));
        assertEquals(mealTwoCalories + mealOneCalories, today.getTotalCalories());
        assertEquals(2, log.size());

    }



    @Test
    public void testIsLogEmpty() {
        assertTrue(today.isLogEmpty());
        assertEquals(0, log.size());

        today.addMealToLogAndDatabase(mealOne);

        assertFalse(today.isLogEmpty());
        assertEquals(1, log.size());

        assertTrue(today.removeMealFromLog(mealOne));

        assertTrue(today.isLogEmpty());
        assertEquals(0, log.size());
    }

    @Test
    public void testGetFullDate() {
        String returnedDate = today.getFullDate();
        String[] splitDate = returnedDate.split(" ");

        String dayOfWeek = splitDate[0];
        String month = splitDate[1];
        String dayNumber = splitDate[2];

        assertTrue(isDayFormatCorrect(dayOfWeek));
        assertTrue(isMonthFormatCorrect(month));
        assertTrue(isDayNumberFormatCorrect(dayNumber));
    }

    @Test
    public void testToJson() {
        toJsonTestHelper("logMeals");
        toJsonTestHelper("dbMeals");
    }

    // MODIFIES: this
    // EFFECTS: helper method to test toJson method using given json key
    public void toJsonTestHelper(String jsonKey) {
        addMultipleIngredients(mealOne, "Beef", "Salsa", "Cheese");
        addMultipleIngredients(mealTwo, "Lettuce", "Tomatoes", "Blue Cheese");

        today.addMealToLogAndDatabase(mealOne);
        today.addMealToLogAndDatabase(mealTwo);
        JSONObject jsonObj = today.toJson();

        JSONArray jsonArray = jsonObj.getJSONArray(jsonKey);
        JSONObject jsonMealOne = jsonArray.getJSONObject(0);
        JSONObject jsonMealTwo = jsonArray.getJSONObject(1);

        assertEquals(jsonMealOne.getString("name"), mealOne.getName());
        assertEquals(jsonMealTwo.getString("name"), mealTwo.getName());
        assertEquals(jsonMealOne.getInt("cals"), mealOne.getCals());
        assertEquals(jsonMealTwo.getInt("cals"), mealTwo.getCals());

        JSONArray ingredientsMealOne = jsonMealOne.getJSONArray("ingredients");
        JSONArray ingredientsMealTwo = jsonMealTwo.getJSONArray("ingredients");
        assertTrueJsonIngredientsList(ingredientsMealOne, mealOne.getIngredients());
        assertTrueJsonIngredientsList(ingredientsMealTwo, mealTwo.getIngredients());
    }

    // EFFECTS: tests and asserts the JSON ARRAY contains the proper ingredients
    public void assertTrueJsonIngredientsList(JSONArray jsonArray, List<String> ingredients) {
        for (Object jsonIngredient: jsonArray) {
            assertTrue(ingredients.contains(jsonIngredient.toString()));
        }
    }

    // MODIFIES: this
    // EFFECTS: adds multiple ingredients to a given meal, returns meal
    public Meal addMultipleIngredients(Meal meal, String... ingredients) {
        for (String ingredient: ingredients) {
            meal.addIngredient(ingredient);
        }
        return meal;
    }

    // MODIFIES: this
    // EFFECTS: gets the latest log calories value
    public void updateLogCalories() {
        logCalories = today.getTotalCalories();
    }

    // REQUIRES: value passed must be a day of the week
    // EFFECTS: helper function to check if value is a valid day of the week
    public Boolean isDayFormatCorrect(String day) {
        String lcDate = day.toLowerCase();

        ArrayList<String> daysOfWeek = new ArrayList<>(Arrays.asList("monday", "tuesday", "wednesday", "thursday",
                "friday", "saturday", "sunday"));

        return (daysOfWeek.contains(lcDate));
    }

    // REQUIRES: value passed must be a month
    // EFFECTS: helper function to check if value is valid month
    public Boolean isMonthFormatCorrect(String month) {
        String lcMonth= month.toLowerCase();

        ArrayList<String> months = new ArrayList<>(Arrays.asList("january", "february", "march", "april", "may",
                "june", "july", "august", "september", "october", "november", "december"));

        return (months.contains(lcMonth));

    }

    // REQUIRES: value passed must be a day number
    // EFFECTS: helper function to check if value is inclusively between 1 and 31
    public Boolean isDayNumberFormatCorrect(String dayNum) {
        ArrayList<String> dayNumbers = new ArrayList<>();

        for (int i = 1; i <=31; i++) {
            dayNumbers.add(Integer.toString(i));
        }

        return (dayNumbers.contains(dayNum));
    }


}
