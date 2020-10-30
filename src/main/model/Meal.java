package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a single meal with a name, calories, and list of ingredients
public class Meal implements Writable {
    private String name;
    private Integer cals;
    private List<String> ingredients;

    // EFFECTS: creates basic meal entry with name and calories
    public Meal(String name, Integer cals) {
        this.name = name;
        this.cals = cals;
        this.ingredients = new ArrayList<>();
    }

    // EFFECTS: overrides equals method to compare different meal objects with the exact same information
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meal meal = (Meal) o;
        return name.equals(meal.name) && cals.equals(meal.cals) && Objects.equals(ingredients, meal.ingredients);
    }

    // EFFECTS: a standard override of hashCode method to override equals method for a meal
    @Override
    public int hashCode() {
        return Objects.hash(name, cals, ingredients);
    }

    // MODIFIES: this
    // EFFECTS: adds an ingredient to a meal entry
    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public Integer getCals() {
        return this.cals;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: converts a meal into a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cals", cals);
        json.put("ingredients", ingredients);
        return json;
    }
}
