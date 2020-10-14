package ui;


import model.Log;
import model.MealDatabase;
import model.Meal;

import java.util.List;
import java.util.Scanner;

// Represents the user interface console
public class TrackerApp {
    private Log today;
    private List<Meal> log;
    private MealDatabase mdbObject;
    private List<Meal> mealDB;
    private Scanner input;

    //EFFECTS: begins the ui console
    public TrackerApp() {
        runTracker();
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: initializes day log and allows for user input
    private void runTracker() {
        boolean appRunning = true;
        String command;

        initializeLog();

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
        switch (command) {
            case "new":
                doNewMeal();
                break;
            case "add":
                doAddMealFromDatabase();
                break;
            case "del":
                doDeleteMeal();
                break;
            case "cals":
                doViewCals();
                break;
            case "log":
                doViewMealLog();
                break;
            case "view":
                doViewMealInDatabase();
                break;
            default:
                System.out.println("Invalid Selection. Please try again.");
                break;
        }
    }


    // EFFECTS: displays main menu
    private void displayMenu() {
        System.out.println("\nWelcome to FoodPal! Please choose an option:");
        System.out.println("\t new -> create new meal");
        System.out.println("\t add -> add meal from database");
        System.out.println("\t del -> delete a meal entry");
        System.out.println("\tcals -> view total calories");
        System.out.println("\t log -> view today's food log");
        System.out.println("\tview -> view a meal");
        System.out.println("\tquit -> quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes main objects
    public void initializeLog() {
        today = new Log();
        log = today.getMealLog();
        mdbObject = today.getMealDatabaseObject();
        mealDB = mdbObject.getMealDatabase();

        input = new Scanner(System.in);
    }

    // REQUIRES: meal name cannot include spaces
    //           calories must be > 0
    // MODIFIES: this
    // EFFECTS: creates a new meal and adds it to today's meal log
    public void doNewMeal() {
        System.out.println("Enter Meal Name:");
        String name = input.next();
        System.out.println("Enter Calories:");
        int cals = input.nextInt();
        System.out.println("Enter Ingredients, type \"done\" when finished:");

        Meal meal = new Meal(name, cals);
        enterIngredients(meal);
        today.addMealToLog(meal);

        System.out.println(meal.getName() + " has been created and added to your log!");
    }

    // REQUIRES: ingredients cannot include spaces
    // MODIFIES: this
    // EFFECTS: allows user to add multiple ingredients to a new meal
    public void enterIngredients(Meal meal) {
        boolean addingIngredients = true;

        while (addingIngredients) {
            String ingredient = input.next();
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
        System.out.println("Select a meal to add from the database by typing its number:");
        Meal meal = selectMeal(mealDB);
        today.addMealToLog(meal);
        System.out.println(meal.getName() + " has been added to your log!");
    }

    // REQUIRES: meal database must not be empty
    // MODIFIES: this
    // EFFECTS: displays database meal options and retrieves meal based on user input
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

    // REQUIRES: meal database must not be empty
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
}
