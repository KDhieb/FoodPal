package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a single meal with a name, calories, and list of ingredients
public class Meal implements Writable {
    private String name;
    private Integer cals;
    private List<String> ingredients;

    //EFFECTS: creates basic meal entry with name and calories
    public Meal(String name, Integer cals) {
        this.name = name;
        this.cals = cals;
        this.ingredients = new ArrayList<>();
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cals", cals);
        json.put("ingredients", ingredients);
        return json;
    }
}
