package ui;

import model.Meal;

import javax.swing.*;
import java.awt.*;

public class MealCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Meal meal = (Meal) value;
        String name = meal.getName();
        int cals = meal.getCals();
        String mealText = "<html>Name: " + name + "<br/>Calories: " + cals;
        setText(mealText);

        return this;
    }
}
