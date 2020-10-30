package persistence;

import model.Log;
import model.Meal;
import model.MealDatabase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: This class has been modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileNotThere.json");
        try {
            Log log = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReaderEmptyLog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLog.json");
        try {
            Log log = reader.read();
            MealDatabase mdbObject = log.getMealDatabaseObject();
            assertEquals(0, mdbObject.getMealDatabase().size());
            assertEquals(0, log.getTotalCalories());
            assertEquals(0, log.getMealLog().size());
            assertTrue(log.isLogEmpty());
        } catch (IOException e) {
            fail("Failed to read from file when testing empty log");
        }
    }

    @Test
    public void testReaderFullLog() {
        JsonReader reader = new JsonReader("./data/testReaderFullLog.json");
        try {
            Log log = reader.read();

            List<Meal> meals = log.getMealLog();
            List<Meal> dbMeals = log.getMealDatabaseObject().getMealDatabase();

            assertEquals(970, log.getTotalCalories());
            assertEquals(3, meals.size());
            assertFalse(log.isLogEmpty());
            assertEquals(5, dbMeals.size());
            assertFalse(log.getMealDatabaseObject().isDatabaseEmpty());

            List<String> mealOneIngredients = Arrays.asList("Beef", "Taco Shell", "Salsa", "Cheese");
            List<String> mealTwoIngredients = Arrays.asList("Chicken", "Lettuce", "Tomato", "Caesar Dressing");
            List<String> mealThreeIngredients = Arrays.asList("Salmon", "Olive Oil", "Spices", "Baby Tomatoes");
            List<String> mealFourIngredients = Arrays.asList("Bell Peppers", "Minced Meat", "Rice", "Tomato Sauce");
            List<String> mealFiveIngredients = Arrays.asList("Tilapia", "Spices", "Olive oil", "onions", "celery");

            checkMealTest("Beef Tacos", 350, mealOneIngredients, meals.get(0), dbMeals.get(0));
            checkMealTest("Chicken Salad", 220, mealTwoIngredients, meals.get(1), dbMeals.get(1));
            checkMealTest("Roasted Salmon", 400, mealThreeIngredients, meals.get(2), dbMeals.get(2));
            checkMealTest("Stuffed Peppers", 360, mealFourIngredients, dbMeals.get(3));
            checkMealTest("Baked Tilapia", 270, mealFiveIngredients, dbMeals.get(4));

        } catch (IOException e) {
            fail("Failed to read from file when testing full log");
        }
    }

}
