package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DayTest {
    Day today;
    Meal mealOne;
    Meal mealTwo;
    int mealOneCalories;
    int mealTwoCalories;
    int calorieBalance;
    int logCalories;
    List<Meal> log;

    @BeforeEach
    public void runBefore() {
        today = new Day();
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
        today.addMealToLog(mealOne);
        updateLogCalories();

        assertEquals(mealOneCalories, logCalories);
        assertEquals(1, log.size());
    }

    @Test
    public void testAddMealToLogTwoDifferentMeal() {
        today.addMealToLog(mealOne);
        today.addMealToLog(mealTwo);

        int calorieBalance = mealOneCalories + mealTwoCalories;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
        assertEquals(2, log.size());
    }


    @Test
    public void testAddMealToLogTwoSameMeals() {
        today.addMealToLog(mealOne);
        today.addMealToLog(mealOne);

        calorieBalance = mealOneCalories * 2;
        updateLogCalories();
        assertEquals(calorieBalance, logCalories);
        assertEquals(2, log.size());
    }

    @Test
    public void testAddMealToLogMealOneTwoOne() {
        today.addMealToLog(mealOne);
        today.addMealToLog(mealTwo);
        today.addMealToLog(mealOne);

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
        today.addMealToLog(mealOne);
        updateLogCalories();
        assertEquals(mealOneCalories, logCalories);
        assertTrue(today.removeMealFromLog(mealOne));
        updateLogCalories();
        assertEquals(0, logCalories);
    }

    @Test
    public void testRemoveMealFromLogTwoDifferentMeals() {
        today.addMealToLog(mealOne);
        today.addMealToLog(mealTwo);

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
        today.addMealToLog(mealOne);
        today.addMealToLog(mealOne);

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
        today.addMealToLog(mealOne);
        today.addMealToLog(mealTwo);
        today.addMealToLog(mealOne);

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
