package ui;

import model.Log;
import model.Meal;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame implements ListSelectionListener {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;

    private JButton newMeal;
    private JButton addMeal;
    private JButton deleteMeal;
    private JButton viewMeal;

    private JList<Meal> mealLog;
    private JList<String> ingredientWindow;
    private DefaultListModel mealListModel;
    private DefaultListModel ingredientListModel;

    private JLabel date;
    private JLabel totalCalories;


    private Log log;

    //Create and set up the window.
    public Gui() {
        super("FoodPal - Calorie and Meal Tracker");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        log = new Log();
        mealListModel = new DefaultListModel();
        ingredientListModel = new DefaultListModel();

        // Delete After Testing
        Meal meal = new Meal("Chicken Tacos", 250);
        Meal mealTwo = new Meal("Salad", 400);
        Meal mealThree = new Meal("Fish", 300);

        mealListModel.addElement(meal);
        mealListModel.addElement(mealTwo);
        mealListModel.addElement(mealThree);

        log.addMealToLogAndDatabase(meal);
        log.addMealToLogAndDatabase(mealTwo);
        log.addMealToLogAndDatabase(mealThree);

        meal.addIngredient("TESTING THIS INGREDIENT");
        meal.addIngredient("Another Test for THIS INGREDIENT");
        mealTwo.addIngredient("Another one!");

        ingredientListModel.addElement("Chicken");
        ingredientListModel.addElement("Salad Dressing");

        initializeHeader();
        initializeLog();
        initializeButtons();

        pack();
        setVisible(true);
    }

    private void initializeHeader() {
        JPanel headerPane = new JPanel();
        headerPane.setLayout(new FlowLayout());

        date = new JLabel(log.getFullDate());
        JPanel datePane = new JPanel();
        datePane.add(date);
        datePane.setPreferredSize(new Dimension(300, 30));
        datePane.setBorder(BorderFactory.createLineBorder(Color.black));

        totalCalories = new JLabel("Total Calories: " + log.getTotalCalories());
        JPanel caloriePane = new JPanel();
        caloriePane.add(totalCalories);
        caloriePane.setPreferredSize(new Dimension(200, 30));
        caloriePane.setBorder(BorderFactory.createLineBorder(Color.black));

        headerPane.add(datePane);
        headerPane.add(Box.createHorizontalStrut(50));
        headerPane.add(caloriePane);
        add(headerPane, BorderLayout.PAGE_START);
    }

    private void initializeLog() {
        JSplitPane logPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        logPane.setOneTouchExpandable(true);
        logPane.setDividerLocation(600);

        JScrollPane mealScrollPane = setupMealPane();

        JScrollPane ingredientScrollPane = setupIngredientPane();

        logPane.add(mealScrollPane);
        logPane.add(ingredientScrollPane);
        logPane.setMinimumSize(new Dimension(700, 300));
        logPane.setPreferredSize(new Dimension(500, 300));
        logPane.setBorder(BorderFactory.createLineBorder(Color.black));
        add(logPane, BorderLayout.CENTER);
    }

    private JScrollPane setupIngredientPane() {
        ingredientWindow = new JList(ingredientListModel);
        ingredientWindow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientWindow.setSelectedIndex(0);
        ingredientWindow.addListSelectionListener(this);
        ingredientWindow.setVisibleRowCount(10);
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientWindow);
        ingredientScrollPane.createVerticalScrollBar();
        ingredientScrollPane.setHorizontalScrollBar(null);
        return ingredientScrollPane;
    }

    private JScrollPane setupMealPane() {
        mealLog = new JList(mealListModel);
        mealLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mealLog.setSelectedIndex(0);
        mealLog.addListSelectionListener(this);
        mealLog.setVisibleRowCount(10);
        mealLog.setCellRenderer(new MealCellRenderer());
        JScrollPane mealScrollPane = new JScrollPane(mealLog);
        mealScrollPane.createVerticalScrollBar();
        mealScrollPane.setHorizontalScrollBar(null);
        return mealScrollPane;
    }

    private void initializeButtons() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(3,1));
        newMeal = new JButton("New Meal");
        addMeal = new JButton("Add Meal");
        deleteMeal = new JButton("Delete Meal");
        deleteMeal.setActionCommand("Delete meal");
        deleteMeal.addActionListener(new DeleteListener());
        viewMeal = new JButton("View Meal");
        buttonPane.add(newMeal);
        buttonPane.add(addMeal);
        buttonPane.add(deleteMeal);
        buttonPane.add(viewMeal);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    private void updateCalories() {
        int calories = log.getTotalCalories();
        totalCalories.setText("Total Calories: " + calories);
    }

    private void updateMealList(int index) {
        log.removeMealFromLog(index);
        mealListModel.remove(index);
    }

    private void updateIngredientsWindow(int index) {
        System.out.println("RECURSION");
        ingredientListModel.clear();
        if (index != -1) {
            System.out.println("THIS IS THE INDEX: " + index);
            Meal meal = log.getMealLog().get(index);
            List<String> ingredients = meal.getIngredients();
            for (String ingredient : ingredients) {
                ingredientListModel.addElement(ingredient);
            }
            System.out.println("NUM OF INGREDIENTS: " + ingredientListModel.size());
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = mealLog.getSelectedIndex();
            if (index < 0) {
                index = 0;
            }
            System.out.println("index selected: " + index);
            updateMealList(index);
            updateCalories();
            System.out.println("should be working");
            index = mealLog.getSelectedIndex();
            updateIngredientsWindow(index);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == true) {
            int index = mealLog.getSelectedIndex();
            System.out.println("INGREDIENT INDEX:" + ingredientWindow.getSelectedIndex());
            updateIngredientsWindow(index);
        }
    }
}

