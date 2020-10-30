package ui;


import model.Log;
import model.MealDatabase;
import model.Meal;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;

// Represents the user interface console
public class TrackerApp {
    private static final String JSON_STORE = "./data/log.json";
    private Log today;
    private List<Meal> log;
    private MealDatabase mdbObject;
    private List<Meal> mealDB;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //EFFECTS: begins the ui console
    public TrackerApp() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        today = new Log();
        log = today.getMealLog();
        mdbObject = today.getMealDatabaseObject();
        mealDB = mdbObject.getMealDatabase();

        input = new Scanner(System.in);

        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: keeps application running and allows for user input
    private void runTracker() {
        boolean appRunning = true;
        String command;

        while (appRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                appRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nHope to see you at your next meal!");
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs in main menu
    private void processCommand(String command) {
        if (command.equals("new")) {
            doNewMeal();
        } else if (command.equals("add")) {
            doAddMealFromDatabase();
        } else if (command.equals("del")) {
            doDeleteMeal();
        } else if (command.equals("cals")) {
            doViewCals();
        } else if (command.equals("log")) {
            doViewMealLog();
        } else if (command.equals("view")) {
            doViewMealInDatabase();
        } else if (command.equals("save")) {
            doSaveLog();
        } else if (command.equals("load")) {
            doLoadLog();
        } else {
            System.out.println("Invalid Selection. Please try again.");
        }
    }


    // EFFECTS: displays main menu
    private void displayMenu() {
        System.out.println("\n");
        System.out.println("\nWelcome to FoodPal! Please choose an option:");
        System.out.println("\t new -> create new meal");
        System.out.println("\t add -> add meal from database");
        System.out.println("\t del -> delete a meal entry");
        System.out.println("\tcals -> view total calories");
        System.out.println("\t log -> view today's food log");
        System.out.println("\tview -> view a meal in database");
        System.out.println("\tsave -> save log to file");
        System.out.println("\tload -> load saved log from file");
        System.out.println("\tquit -> quit");
    }

//    // MODIFIES: this
//    // EFFECTS: initializes main objects
//    public void initializeLog() {
//        // nothing
//    }

    // REQUIRES: calories entered must be >= 0
    // MODIFIES: this
    // EFFECTS: creates a new meal and adds it to today's meal log
    public void doNewMeal() {
        System.out.println("Enter Meal Name:");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Enter Calories:");
        int cals = input.nextInt();
        Meal meal = new Meal(name, cals);
        enterIngredients(meal);
        today.addMealToLogAndDatabase(meal);

        System.out.println(meal.getName() + " has been created and added to your log!");
    }

    // MODIFIES: this
    // EFFECTS: allows user to add multiple ingredients to a new meal
    public void enterIngredients(Meal meal) {
        System.out.println("Enter Ingredients, type \"done\" when finished:");
        boolean addingIngredients = true;

        input.nextLine();
        while (addingIngredients) {
            String ingredient = input.nextLine();
            if (ingredient.toLowerCase().equals("done")) {
                addingIngredients = false;
            } else {
                meal.addIngredient(ingredient);
                System.out.println("Enter next ingredient, type \"done\" when finished:");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a meal from the database to today's meal log and updates calories
    public void doAddMealFromDatabase() {
        if (mdbObject.isDatabaseEmpty()) {
            System.out.println("Your meal database is empty! Add a meal first.");
        } else {
            System.out.println("Select a meal to add from the database by typing its number:");
            Meal meal = selectMeal(mealDB);
            today.addMealToLogAndDatabase(meal);
            System.out.println(meal.getName() + " has been added to your log!");
        }
    }

    // REQUIRES: passed meal list must not be empty
    // MODIFIES: this
    // EFFECTS: displays meal options from a given meal list
    //          returns a meal based on user selection
    public Meal selectMeal(List<Meal> mealCollection) {
        for (Meal meal: mealCollection) {
            System.out.println("For " + meal.getName() + " press -> " + mealCollection.indexOf(meal));
        }
        int index = input.nextInt();
        return mealCollection.get(index);
    }

    // MODIFIES: this
    // EFFECTS: remove a meal from meal log and updates calories
    private void doDeleteMeal() {
        if (today.isLogEmpty()) {
            System.out.println("There are no meals in your log to delete!");
        } else {
            System.out.println("Select a meal to remove by typing its number:");
            Meal meal = selectMeal(log);
            today.removeMealFromLog(meal);
            System.out.println(meal.getName() + " has been removed from your log!");
        }
    }

    // EFFECTS: displays total calories consumed
    public void doViewCals() {
        int calories = today.getTotalCalories();
        System.out.println("Total calories consumed today: " + calories + " cals");
    }

    // EFFECTS: displays today's date and meal log
    public void doViewMealLog() {
        int mealNumber = 0;

        System.out.println("Meals for " + today.getFullDate());
        System.out.println("-----------------------------");

        for (Meal meal: log) {
            mealNumber++;
            System.out.println("Meal #" + mealNumber);
            printMealInfo(meal);
            System.out.println("-----------------");

        }
    }

    // EFFECTS: prints a meal's name, calories, and ingredients
    public void printMealInfo(Meal meal) {
        String name = meal.getName();
        int cals = meal.getCals();
        List<String> ingredients = meal.getIngredients();

        System.out.println("Name: " + name);
        System.out.println("Calories: " + cals);
        System.out.println("Ingredients: " + ingredients);
    }

    // EFFECTS: searches database and displays a user selected meal's information
    public void doViewMealInDatabase() {
        if (mdbObject.isDatabaseEmpty()) {
            System.out.println("There are no meals in your database yet! Add a meal first!");
        } else {
            System.out.println("Select a meal from the database:");
            Meal meal = selectMeal(mealDB);
            System.out.println("Meal Details:");
            printMealInfo(meal);
        }
    }

    // EFFECTS: saves the current log to file
    private void doSaveLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(today);
            jsonWriter.close();
            System.out.println("Saved log for " + today.getFullDate() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads log from file
    private void doLoadLog() {
        try {
            today = jsonReader.read();
            log = today.getMealLog();
            mdbObject = today.getMealDatabaseObject();
            mealDB = mdbObject.getMealDatabase();

            System.out.println("Log successfully loaded from file!");

        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }
}
