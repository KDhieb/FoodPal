package persistence;

import model.Log;
import model.Meal;
import model.MealDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void checkMeal(String name, int cals, List<String> ingredients, Meal meal) {
        assertEquals(name, meal.getName());
        assertEquals(cals, meal.getCals());

        for (String ingredient: meal.getIngredients()) {
            assertTrue(ingredients.contains(ingredient));
        }
    }
}
