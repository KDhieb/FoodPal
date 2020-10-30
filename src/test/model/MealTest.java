package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealTest {
    private Meal mealOne;
    private Meal mealTwo;

    @BeforeEach
    public void runBefore() {
        mealOne = new Meal("Burrito", 350);
        mealTwo = new Meal("Pizza", 500);
    }

    @Test
    public void testAddIngredients() {
        List<String> ingredients = mealOne.getIngredients();
        mealOne.addIngredient("Beef");

        assertEquals(1, ingredients.size());

        mealOne.addIngredient("Tortilla");
        mealOne.addIngredient("Cheese");
        mealOne.addIngredient("Salsa");
        ingredients = mealOne.getIngredients();

        assertEquals(4, ingredients.size());

        assertEquals("Beef", ingredients.get(0));
        assertEquals("Tortilla", ingredients.get(1));
        assertEquals("Cheese", ingredients.get(2));
        assertEquals("Salsa", ingredients.get(3));
    }

    @Test
    public void testEqualsAndHashCode() {
        String mealOneName = mealOne.getName();
        int mealOneCals = mealOne.getCals();
        Meal mealOneCopy = new Meal(mealOneName, mealOneCals);
        Meal nullMeal = null;

        List<String> ingredients = Arrays.asList("Beef", "Cheese", "Sour Cream");

        for (String ingredient: ingredients) {
            mealOne.addIngredient(ingredient);
            mealOneCopy.addIngredient(ingredient);
        }

        mealTwo.addIngredient("Spicy Sauce");

        assertTrue(mealOne.equals(mealOne));
        assertTrue(mealOne.equals(mealOneCopy));
        assertTrue(mealOneCopy.equals(mealOne));
        assertFalse(mealOne.equals(mealTwo));
        assertFalse(mealOne.equals("Not a Meal Object"));
        assertFalse(mealOne.equals(nullMeal));

        assertEquals(mealOne.hashCode(), mealOneCopy.hashCode());
    }

    @Test
    public void testEqualsAlmostEquals() {
        String name = mealOne.getName();
        int cals = mealOne.getCals();
        String ingredient = "Extra Cheese";
        mealOne.addIngredient(ingredient);

        Meal sameName = new Meal(name, cals + 1);
        Meal sameCals = new Meal(name + "!", cals);
        Meal sameNameCals = new Meal(name, cals);

        Meal sameIngredient = new Meal(name + "!", cals + 1);
        sameIngredient.addIngredient(ingredient);

        Meal sameNameIngredient = new Meal(name, cals + 1);
        sameNameIngredient.addIngredient(ingredient);

        Meal sameCalsIngredient = new Meal(name + "!", cals);
        sameCalsIngredient.addIngredient(ingredient);

        assertFalse(mealOne.equals(sameName));
        assertFalse(mealOne.equals(sameCals));
        assertFalse(mealOne.equals(sameNameCals));
        assertFalse(mealOne.equals(sameIngredient));
        assertFalse(mealOne.equals(sameNameIngredient));
        assertFalse(mealOne.equals(sameCalsIngredient));
    }


}
