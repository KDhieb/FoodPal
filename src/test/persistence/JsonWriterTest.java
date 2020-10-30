package persistence;

import model.Log;
import model.Meal;
import model.MealDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: This class has been modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest {
    Meal mealOne;
    Meal mealTwo;
    Meal mealThree;

    @BeforeEach
    public void setup() {
        mealOne = new Meal("Chicken Salad", 200);
        mealOne.addIngredient("Chicken");
        mealOne.addIngredient("Lettuce");
        mealOne.addIngredient("Caesar Dressing");

        mealTwo = new Meal("Beef Tacos", 320);
        mealTwo.addIngredient("Beef");
        mealTwo.addIngredient("Taco Shell");
        mealTwo.addIngredient("Guacamole");

        mealThree = new Meal("Pizza", 550);
        mealThree.addIngredient("Tomato Sauce");
        mealThree.addIngredient("Cheese");
        mealThree.addIngredient("Anchovies");
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/invalid\0FileName.json");
            writer.open();
            fail("IOException expected but not thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyLog() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLog.json");
            log = reader.read();
            assertTrue(log.isLogEmpty());
            assertEquals(0, log.getTotalCalories());
            assertEquals(0, log.getMealLog().size());
            MealDatabase mdbObject = log.getMealDatabaseObject();
            assertEquals(0, mdbObject.getMealDatabase().size());
        } catch (IOException e) {
            fail("IOException thrown, no exception expected");
        }
    }


    @Test
    public void testWriterFullLog() {
        try {
            Log log = new Log();
            log.addMealToLogAndDatabase(mealOne);
            log.addMealToLogAndDatabase(mealTwo);
            log.addMealToLogAndDatabase(mealThree);

            JsonWriter writer = new JsonWriter("./data/testWriterFullLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFullLog.json");
            log = reader.read();
            MealDatabase mdbObject = log.getMealDatabaseObject();

            assertFalse(log.isLogEmpty());
            assertEquals(1070, log.getTotalCalories());
            assertEquals(3, log.getMealLog().size());
            assertEquals(3, mdbObject.getMealDatabase().size());

            List<Meal> meals = log.getMealLog();
            List<Meal> dbMeals = log.getMealDatabaseObject().getMealDatabase();
            checkMealTest("Chicken Salad", 200, mealOne.getIngredients(), meals.get(0), dbMeals.get(0));
            checkMealTest("Beef Tacos", 320, mealTwo.getIngredients(), meals.get(1), dbMeals.get(1));
            checkMealTest("Pizza", 550, mealThree.getIngredients(), meals.get(2), dbMeals.get(2));

        } catch (IOException e) {
            fail("IOException thrown, no exception expected");
        }
    }




}
