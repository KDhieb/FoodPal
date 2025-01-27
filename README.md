# FoodPal

## A Daily Meal and Calorie Tracker

#### What this application does

This application allows users to track the food they consume 
on a daily basis to monitor their dietary and nutritional habits. 
You can also *store your frequently consumed meals* to refer to and 
add again in the future. The application also allows you to *track
the calories and ingredients of each meal*, and can *display the 
total daily calories* consumed. You may also refer back
to past days and *view previous entries*.

#### Who should use this application


This application is directed towards health conscious individuals
and people who are trying to improve their dietary habits by 
understanding what they eat on a daily basis. This application can
serve as a way to hold yourself accountable for your diet and to
gain control on how many calories you consume every day. People who
also want to track recipe ingredients can use this application
as a way to store that information.

#### Why this project is of interest to me

Being a health conscious person myself, I appreciate software solutions
that aim to facilitate healthier lifestyle choices. Given that I have used 
similar applications in the past, I would see myself personally using this
platform. Also, by being familiar with health applications, 
I believe that I am in a good position to add my own value and differentiation
to this kind of solution.

#### Some features of this application can include:
- Track daily meals eaten and their ingredients
- Track calories per meal
- View daily calories consumed
- View and display current and previous days
- View and add previously stored meals

## User Stories
- As a user, I want to be able to add a meal to my daily diary
- As a user, I want to be able to view the list of all the meals I ate today
- As a user, I want to be able to select a meal and view its ingredients and calories
- As a user, I want to be able to view my total daily calories consumed
- As a user, I want to be able to save my daily meal entries to file
- As a user, I want to be able to load my daily meal entries from file

## Phase 4: Task 2

- Option Chosen: Robust class Implementation (first option)
- Class involved: Meal Class
- Method Involved: Meal Constructor (throws InvalidInputException)


## Phase 4: Task 3

Reflection on refactoring to improve project design:

- Refactor code to reduce coupling between Log and MealDatabase with 
 the Meal class

- Split my ActionListener class within my Gui class into individual
 ActionListener classes for each button to prevent overwhelming the 
 class with if/else statements while keeping control of each button separate
 
- Implement an abstract class (such as a generic meal log class) for Log and MealDatabase 
to extend such that common functionality can be shared while eliminating repeated code
 
 - Reduce dependencies of Gui class (i.e., MealCellRenderer or NewMealWindow)
by refactoring class and making all features self-contained



