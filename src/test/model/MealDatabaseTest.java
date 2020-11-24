package model;

import exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealDatabaseTest {
    Log today;
    MealDatabase mdbObject;
    Meal mealOne;
    Meal mealTwo;

    @BeforeEach
    public void runBefore() throws InvalidInputException {
        today = new Log();
        mdbObject = today.getMealDatabaseObject();
        mealOne = new Meal("Burrito", 350);
        mealTwo = new Meal("Salad", 200);
    }

    @Test
    public void testConstructorSuccess() {
        try {
            Meal testMeal = new Meal("TestMeal", 0);
        } catch (InvalidInputException e) {
            fail("No Exception Expected");
        }
    }

    @Test
    public void testConstructorNegativeCalories() {
        try {
            Meal testMeal = new Meal("TestMeal", -1);
            fail("InvalidMealInputException expected!");
        } catch (InvalidInputException e) {
            // expected
        }
    }

    @Test
    public void testStoreEntry(){
        List<Meal> mealDatabase = mdbObject.getMealDatabase();

        Boolean stored = mdbObject.storeEntry(mealOne);
        assertEquals(1, mealDatabase.size());
        assertTrue(stored);

        stored = mdbObject.storeEntry(mealTwo);
        assertEquals(2, mealDatabase.size());
        assertTrue(stored);

        stored = mdbObject.storeEntry(mealOne);
        assertEquals(2, mealDatabase.size());
        assertFalse(stored);
    }

    @Test
    public void testGetMealByIndex() {
        mdbObject.storeEntry(mealOne);
        mdbObject.storeEntry(mealTwo);

        Meal getMealOne = mdbObject.getMealByIndex(0);
        Meal getMealTwo = mdbObject.getMealByIndex(1);

        assertEquals(mealOne.getName(), getMealOne.getName());
        assertEquals(mealTwo.getName(), getMealTwo.getName());
    }

    @Test
    public void testIsDatabaseEmpty() {
        assertTrue(mdbObject.isDatabaseEmpty());
        today.addMealToLogAndDatabase(mealOne);
        assertFalse(mdbObject.isDatabaseEmpty());
    }
}
