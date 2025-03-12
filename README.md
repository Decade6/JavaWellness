# WellnessApp
# Android Studio Version 2021.1.1

A diet/wellness/fitness app, similar to “Lose it!”

Displays to a user a database of meals and recipes that are categorized by their ingredients, diet, and overall   
nutrition. Allows users to keep track of what ingredients they currently have in their ‘pantry’ and search for recipes  
based on that information. If a recipe contains ingredients that the user does not have, they can add those  
ingredients to a ‘shopping list’ to keep track of. The app also contains functionality for tracking calories consumed  
and exercise done on a specific day
 

 
## Accomplishments and overall project status during this increment
 
 - Added the ability for users to create and log in to an account
 
 - Added the ability to track workouts
 
 - User manually inputs workout information (Description, Duration, Calories Burned)
 
 - Added ability to set goals for calories consumed/burned in a day
 
 - Added a section to create and store custom recipes/meals for each day
 
 - Manually created by user, can add title, amount of calories, ingredients, and amount of
 carbs/protein
 
 - Also able to add ingredients manually
 
 - Added a shopping list for tracking ingredients to purchase later
 
 - Add custom shopping list with name of the item, description, and quantity
 
 - Ability to edit/remove an item from shopping list
 
 - Ability to clear all data from shopping list
 
 - Made the list scrollable and added a check box to each item so the user can easily keep
 track of what items they have bought
 
 - Periodic database cleaning for Exercises
 
 - clear table for exercises at 12:00am so that new exercises can be recorded for next day
 
 - Updated Launch screen with more info button to display information about the app
 
 - Added ability for user to login to app with firebase



 ## Challenges, changes in the plan and scope of the project and things that went wrong during this increment
 
 
 The scope of the project in general ended up being more ambitious than initially thought, however the
 fundamental ideas for the project were able to be realized.
 
 - The implementation of the Spoonacular/Recipe API proved to be more difficult to implement
 than anticipated and was not successful. Instead of implementing the API with Gradle, we had to
 concede to making a manual-input method for adding foods/recipes to the list.
 
 - The implementation of MongoDB was also difficult due to lack of experience using it on the
 Android platform. We were able to get it working at a basic level and data does persist, but we
 were unable to fully implement a cross-device database or account system.
 
 - Our initial plan for the project was to have multiple different pages for finding recipes, listing
 what ingredients you have, and planning out your meals for the day. However, due to
 complications with the API we were unable to properly get all of these functions working
 together as initially planned. Without a way of pulling existing recipes from a database, it was
 more efficient to implement these features into a single page and allow the user to manually enter
 their meal information.
 
