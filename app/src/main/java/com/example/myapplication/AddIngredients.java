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

public class AddIngredients extends DialogFragment {
    TextView Title;
    String title;
    EditText addItems;
    String ingredients;
    ArrayList<Food>recipesList;
    int position;
    TextView Ingredients;


    public AddIngredients(String title,String ingredients,ArrayList<Food>recipes,int pos,TextView Ingredients)
    {
        this.title = title;
        this.ingredients = ingredients;
        this.recipesList = recipes;
        this.position = pos;
        this.Ingredients = Ingredients;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ingredient_info, null);
        Title = view.findViewById(R.id.title8);
        Title.setText("Food: " + title);
        addItems = view.findViewById(R.id.Additems);
        addItems.setText(ingredients);
        builder.setView(view)
                .setTitle("Add Ingredients")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Log.d("", String.valueOf(addItems.getText()));
                        recipesList.get(position).setIngredients(addItems.getText().toString());
                        Ingredients.setText(recipesList.get(position).getIngredients());

                    }
                });







        return builder.create();
    }

}
