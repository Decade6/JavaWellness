//Dialog that shows information about food
package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FoodInfo extends DialogFragment {
    TextView Title;
    TextView Calories;
    TextView Ingredients;
    TextView Carbs;
    TextView Summary;
    EditText AddIngredients;
    String title;
    int calories;
    String ingredients;
    String carbs;
    String summary;


    public FoodInfo(String title, int calories, String ingredients, String carbs, String summary)
    {
        this.title = title;
        this.calories = calories;
        this.ingredients = ingredients;
        this.carbs = carbs;
        this.summary = summary;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_info, null);
        Title = view.findViewById(R.id.FTitle);
        Calories = view.findViewById(R.id.FCalories);
        Ingredients = view.findViewById(R.id.FIngredents);
        Carbs = view.findViewById(R.id.FCarbs);
        Summary = view.findViewById(R.id.FSummary);
        //AddIngredients = view.findViewById(R.id.FAdditional);
        Title.setText(title);
        Calories.setText("Calories: " + calories);
        Ingredients.setText("Ingredients: " + ingredients);
        Carbs.setText("Carbs: " + carbs);
        Summary.setText("Summary: " + summary);

        builder.setView(view)
                .setTitle("Additional Info")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });







        return builder.create();
    }



}
