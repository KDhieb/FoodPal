package ui;

import model.Log;
import model.Meal;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

// represents graphical user interface
public class Gui extends JFrame implements ListSelectionListener {
    private static final String JSON_STORE = "./data/log.json";
    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;

    private DefaultListModel<Meal> mealListModel;
    private DefaultListModel<String> ingredientListModel;

    private JList<Meal> mealLog;
    private JLabel totalCalories;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Log log;
    private ImageIcon mealIcon;

    // EFFECTS: Create and set up the main application window.
    public Gui() {
        super("FoodPal - Calorie and Meal Tracker");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeBackend();
        initializeHeader();
        initializeLog();
        initializeButtons();
        pack();
        setVisible(true);

        initializeLoadPrompt();
        exitSavePrompt();
    }

    // MODIFIES: this
    // EFFECTS: initializes main model and data fields
    private void initializeBackend() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        log = new Log();
        mealIcon = new ImageIcon("./data/food1.jpg");
        mealListModel = new DefaultListModel<>();
        ingredientListModel = new DefaultListModel<>();
    }

    // MODIFIES: this
    // EFFECTS: initializes load prompt popup window on start
    private void initializeLoadPrompt() {
        int loadOption = JOptionPane.showConfirmDialog(null,
                "Would you like to load your last log?", "Load File",
                JOptionPane.YES_NO_OPTION);
        if (loadOption == JOptionPane.YES_OPTION) {
            try {
                log = jsonReader.read();
                updateMeals();
            } catch (IOException e) {
                System.out.println("Unable to read from file " + JSON_STORE);
            }
        }
    }

    // EFFECTS: initializes save prompt popup window when quitting
    private void exitSavePrompt() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                int saveOption = JOptionPane.showConfirmDialog(null,
                        "Would you like to save your log before exiting?", "Save File Prompt",
                        JOptionPane.YES_NO_OPTION);
                if (saveOption == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(log);
                        jsonWriter.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                    dispose();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes header pane for data and calories
    private void initializeHeader() {
        JPanel headerPane = new JPanel();
        headerPane.setLayout(new FlowLayout());

        JLabel date = new JLabel(log.getFullDate());
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

    // MODIFIES: this
    // EFFECTS: initializes middle split pane for meal entries and ingredients
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

    // MODIFIES: this
    // EFFECTS: setup configurations for ingredients pane
    private JScrollPane setupIngredientPane() {
        JList<String> ingredientWindow = new JList<>(ingredientListModel);
        ingredientWindow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientWindow.setSelectedIndex(0);
        ingredientWindow.setVisibleRowCount(10);

        JScrollPane ingredientScrollPane = new JScrollPane(ingredientWindow);
        ingredientScrollPane.createVerticalScrollBar();
        ingredientScrollPane.setHorizontalScrollBar(null);
        return ingredientScrollPane;
    }

    // MODIFIES: this
    // EFFECTS: setup configurations for meal pane
    private JScrollPane setupMealPane() {
        mealLog = new JList<>(mealListModel);
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

    // MODIFIES: this
    // EFFECTS: initializes buttons
    private void initializeButtons() {
        JPanel buttonPane = new JPanel();
        //buttonPane.setLayout(new GridLayout(3,1));
        buttonPane.setLayout(new FlowLayout());

        JButton newMeal = new JButton("New Meal");
        newMeal.setActionCommand("New Meal");
        newMeal.addActionListener(new NewMealListener());

        JButton addMeal = new JButton("Add Meal");
        addMeal.setActionCommand("Add Meal");
        addMeal.addActionListener(new AddMealListener());

        JButton deleteMeal = new JButton("Delete Meal");
        deleteMeal.setActionCommand("Delete meal");
        deleteMeal.addActionListener(new DeleteListener());

        buttonPane.add(newMeal);
        buttonPane.add(addMeal);
        buttonPane.add(deleteMeal);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // EFFECTS: updates calories on screen from backend
    private void updateCalories() {
        int calories = log.getTotalCalories();
        totalCalories.setText("Total Calories: " + calories);
    }

    // EFFECTS: updates meal and calories on screen from backend
    private void updateMeals() {
        mealListModel.clear();
        List<Meal> mealLog = log.getMealLog();
        for (Meal meal : mealLog) {
            mealListModel.addElement(meal);
        }
        updateCalories();
    }

    // EFFECTS: updates ingredients window given currently selected list cell index
    private void updateIngredientsWindow(int index) {
        ingredientListModel.clear();
        if (index != -1) {
            Meal meal = log.getMealLog().get(index);
            List<String> ingredients = meal.getIngredients();
            for (String ingredient : ingredients) {
                ingredientListModel.addElement(ingredient);
            }
        }
    }

    // Creates Action listener for new meal button
    class NewMealListener implements ActionListener {

        // MODIFIES: this, log
        // EFFECTS: creates popup window and interface for new meal creation,
        // adds meal to log and database if a meal was created and updates screen
        @Override
        public void actionPerformed(ActionEvent e) {
            NewMealWindow newMealWindow = new NewMealWindow();
            JPanel panel = newMealWindow.returnJPanel();

            int optionPaneValue = JOptionPane.showConfirmDialog(
                    null, panel,
                    "Create New Meal",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, mealIcon);

            if (optionPaneValue == JOptionPane.OK_OPTION) {
                String mealName = newMealWindow.getMealName();
                int calories = newMealWindow.getCalories();
                Meal newMeal = new Meal(mealName, calories);
                List<String> ingredients = newMealWindow.parseAndReturnIngredientList();

                for (String ingredient : ingredients) {
                    newMeal.addIngredient(ingredient.trim());
                }

                log.addMealToLogAndDatabase(newMeal);
                updateMeals();
                int index = mealListModel.indexOf(newMeal);
                mealLog.setSelectedIndex(index);
                updateIngredientsWindow(index);
            }
        }
    }

    // Creates Action listener for add meal button
    class AddMealListener implements ActionListener {

        // MODIFIES: this, log
        // EFFECTS: creates popup window and interface for adding meals from database,
        // adds meal to log if a meal was selected and updates screen
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel(new BorderLayout());
            List<Meal> mealDB = log.getMealDatabaseObject().getMealDatabase();
            String[] mealStringList = new String[mealDB.size()];
            int pos = 0;
            for (Meal meal: mealDB) {
                String name = meal.getName();
                int cals = meal.getCals();
                mealStringList[pos] = String.format("%s  -  %d cals", name, cals);
                pos++;
            }

            Object selectedMeal = JOptionPane.showInputDialog(panel,
                    "Select a meal", "Add meal from database",
                    JOptionPane.QUESTION_MESSAGE, mealIcon,
                    mealStringList, 1);

            if (selectedMeal != null) {
                String selectedMealValue = selectedMeal.toString();
                int selectedMealIndex = Arrays.asList(mealStringList).indexOf(selectedMealValue);
                Meal meal = mealDB.get(selectedMealIndex);
                log.addMealToLog(meal);
                updateMeals();
            }
        }


    }

    // Creates Action listener for delete meal button
    class DeleteListener implements ActionListener {

        // MODIFIES: this, log
        // EFFECTS: Deletes a selected meal from log and updates screen
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int index = mealLog.getSelectedIndex();
                log.removeMealFromLog(index);
                updateMeals();
                index = mealLog.getSelectedIndex();
                updateIngredientsWindow(index);
            } catch (ArrayIndexOutOfBoundsException exception) {
                // Do nothing
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: monitors selected meal index for change on mouse click and updates ingredients window
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            int index = mealLog.getSelectedIndex();
            updateIngredientsWindow(index);
        }
    }
}

