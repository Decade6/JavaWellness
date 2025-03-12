//Food class
package com.example.myapplication;

import androidx.annotation.Nullable;

public class Food {

        String Title;
        int Calories;
        String Carbs;
        String Protein;
        String AdditionalInfo;
        String Ingredients;
        String Summary;


        public Food(String title, int calories,String carbs, String protein,String additionalInfo,String ingredients,String summary)
        {
            this.Title = title;
            this.Calories = calories;
            this.Carbs = carbs;
            this.Protein = protein;
            this.AdditionalInfo = additionalInfo;
            this.Ingredients = ingredients;
            this.Summary = summary;
        }

        public String getTitle(){
            return Title;
        }
        public void setTitle(String title){
            Title = title;
        }
        public int getCalories(){
            return Calories;
        }
        public void setCalories(int calories){
            Calories = calories;
        }
        public String getCarbs(){
            return Carbs;
        }
        public void setCarbs(String carbs){
            Carbs = carbs;
        }
        public String getProtein(){
            return Protein;
        }
        public void setProtein(String protein){
            Protein = protein;
        }
        public String getAdditionalInfo(){
            return AdditionalInfo;
        }
        public void setAdditionalInfo(String info){
            AdditionalInfo = info;
        }
        public String getIngredients(){
            return Ingredients;
        }
        public void setIngredients(String ingredients){
            Ingredients = ingredients;
        }
        public String getSummary(){
            return Summary;
        }
        public void setSummary(String summary){
            Summary = summary;
        }


    public boolean equals(Food food) {
        return this.Title.equalsIgnoreCase(food.getTitle()) && this.Calories == food.getCalories() && this.Carbs.equalsIgnoreCase(food.getCarbs()) && this.Protein.equalsIgnoreCase(food.getProtein()) && this.AdditionalInfo.equalsIgnoreCase(food.getAdditionalInfo())  && this.Ingredients.equalsIgnoreCase(food.getIngredients()) && this.Summary.equalsIgnoreCase(food.getSummary());
    }
}
