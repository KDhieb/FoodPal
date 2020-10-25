package ui;

import java.io.FileNotFoundException;

// represents the main program execution class
public class Main {

    //EFFECTS: runs the food tracker application
    public static void main(String[] args) {
        try {
            new TrackerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run FoodTracker: file not found");
        }
    }
}
