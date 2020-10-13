package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MealTest {
    private Meal meal;

    @BeforeEach
    public void runBefore() {
        meal = new Meal("Burrito", 350);
    }

    @Test
    public void testAddIngredients() {
        List<String> ingredients = meal.getIngredients();
        meal.addIngredient("Beef");

        assertEquals(1, ingredients.size());

        meal.addIngredient("Tortilla");
        meal.addIngredient("Cheese");
        meal.addIngredient("Salsa");
        ingredients = meal.getIngredients();

        assertEquals(4, ingredients.size());

        assertEquals("Beef", ingredients.get(0));
        assertEquals("Tortilla", ingredients.get(1));
        assertEquals("Cheese", ingredients.get(2));
        assertEquals("Salsa", ingredients.get(3));
    }


}
