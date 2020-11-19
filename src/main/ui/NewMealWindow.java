package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

// represents the new meal creation popup window
public class NewMealWindow {
    JFrame frame;
    JPanel panel;
    JTextField mealName;
    JTextField calories;
    JTextArea ingredients;
    JLabel mealNameLabel;
    JLabel caloriesLabel;
    JLabel ingredientsLabel;
    JScrollPane scrollPane;

    // EFFECTS: creates new meal creation window
    public NewMealWindow() {
        initializeFields();

        panel.add(mealNameLabel);
        panel.add(mealName);
        panel.add(caloriesLabel);
        panel.add(calories);
        panel.add(ingredientsLabel);
        panel.add(scrollPane);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMinimumSize(new Dimension(250,200));
        panel.setPreferredSize(new Dimension(250,200));
    }

    // EFFECTS: initializes meal creation fields
    private void initializeFields() {
        frame = new JFrame("New Meal");
        panel = new JPanel();

        mealNameLabel = new JLabel("Enter Meal Name");
        caloriesLabel = new JLabel("Enter Calories");
        ingredientsLabel = new JLabel("Enter Ingredients (comma separated)");

        mealName = new JTextField();
        mealName.setMaximumSize(new Dimension(300, 25));
        calories = new JTextField();
        calories.setMaximumSize(new Dimension(300, 25));

        ingredients = new JTextArea();
        ingredients.setLineWrap(true);
        scrollPane = new JScrollPane(ingredients);
    }

    public JPanel returnJPanel() {
        return panel;
    }

    // EFFECTS: retrieves meal name entered
    public String getMealName() {
        return mealName.getText();
    }

    // EFFECTS: parses calories text String and returns integer
    public int getCalories() {
        return Integer.parseInt(calories.getText());
    }

    // EFFECTS: parses comma separated String of ingredients and returns it as a list
    public List<String> parseAndReturnIngredientList() {
        String ingredientString = ingredients.getText();
        return Arrays.asList(ingredientString.split(","));
    }





}
