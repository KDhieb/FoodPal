package persistence;

import model.Meal;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// CITATION: This class has been modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {

    // EFFECTS: Compares given meal(s) with entered data
    protected void checkMealTest(String name, int cals, List<String> ingredients, Meal... meals) {
        for (Meal meal: meals) {
            assertEquals(name, meal.getName());
            assertEquals(cals, meal.getCals());

            for (String ingredient : meal.getIngredients()) {
                assertTrue(ingredients.contains(ingredient));
            }
        }
    }
}
