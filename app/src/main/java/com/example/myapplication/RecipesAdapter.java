//Recipes adapter for recyclerview
package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    Context context;
    ArrayList<Food> recipeArrayList;

    public RecipesAdapter(Context context, ArrayList<Food> foodArrayList){
        this.context = context;
        this.recipeArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup holder, int type){
        View view = LayoutInflater.from(holder.getContext()).inflate(R.layout.recipe_item, holder, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos){
        String food_item = recipeArrayList.get(pos).getTitle();
        int calories = recipeArrayList.get(pos).getCalories();
        String ingredients = recipeArrayList.get(pos).getIngredients();
        String carbs = recipeArrayList.get(pos).getCarbs();
        holder.title.setText(food_item);
        holder.calories.setText("Calories: " + calories );
        holder.Ingredients.setText("Ingredients: " + ingredients);
        holder.Carbs.setText("Carbs: " + carbs);


        holder.AddCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mPref = context.getSharedPreferences("user_profile", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPref.edit();
                editor.putFloat("food_calories", mPref.getFloat("food_calories",0) + recipeArrayList.get(holder.getAdapterPosition()).getCalories());
                editor.commit();
                Snackbar.make(view, "Calories Added!", Snackbar.LENGTH_SHORT).show();
            }
        });
        holder.additionalinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FoodInfo info = new FoodInfo(recipeArrayList.get(position).getTitle(),recipeArrayList.get(position).getCalories(),recipeArrayList.get(position).getIngredients(),recipeArrayList.get(position).getCarbs(),recipeArrayList.get(position).getSummary());
                info.show(((LaunchScreen)context).getSupportFragmentManager().beginTransaction(), "food info");
            }
        });
        holder.Additionalitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                AddIngredients info = new AddIngredients(recipeArrayList.get(position).getTitle(),recipeArrayList.get(position).getIngredients(),recipeArrayList,position,holder.Ingredients);
                info.show(((LaunchScreen)context).getSupportFragmentManager().beginTransaction(), "food ingredients");


            }
        });






    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView calories;
        TextView Ingredients;
        TextView Carbs;
        Button additionalinfo;
        Button AddCalories;
        Button Additionalitems;
        Button add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.addButton);
            title = itemView.findViewById(R.id.recipes_titleR);
            calories = itemView.findViewById(R.id.caloriesTextR);
            Ingredients = itemView.findViewById(R.id.ingrediantsTextR);
            Carbs = itemView.findViewById(R.id.carbsTextR);
            additionalinfo = itemView.findViewById(R.id.additionalinfoR);
            AddCalories = itemView.findViewById(R.id.AddCalories);
            Additionalitems = itemView.findViewById(R.id.FAdditional);

        }
    }

}

