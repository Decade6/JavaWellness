package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class newRecipe extends DialogFragment {
    EditText ITitle;
    EditText ICalories;
    EditText IIngredients;
    EditText ICarbs;
    EditText IProtein;
    EditText ISummary;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_recipe, null);
        ITitle = view.findViewById(R.id.ITitle);
        ICalories = view.findViewById(R.id.ICalories);
        IIngredients = view.findViewById(R.id.IIngredients);
        ICarbs = view.findViewById(R.id.ICarbs);
        IProtein = view.findViewById(R.id.IProtein);
        ISummary = view.findViewById(R.id.ISummary);
        builder.setView(view)
                .setTitle("Create Recipe")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContentValues mValues = new ContentValues();
                        mValues.put("Title",ITitle.getText().toString().trim());
                        mValues.put("Calories",ICalories.getText().toString().trim());
                        mValues.put("Carbs",ICarbs.getText().toString().trim());
                        mValues.put("Protein",IProtein.getText().toString().trim());
                        mValues.put("AdditionalInfo","");
                        mValues.put("Ingredients",IIngredients.getText().toString().trim());
                        mValues.put("Summary",ISummary.getText().toString().trim());

                        getContext().getContentResolver().insert(RecipesProvider.CONTENT_URI,mValues);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        return builder.create();
    }

}
