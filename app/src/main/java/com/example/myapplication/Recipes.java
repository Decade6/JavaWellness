//recipes fragment
package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Recipes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recipes extends Fragment {

    RecyclerView recipelistR;
    RecipesAdapter foods;
    ArrayList<Food> recipe_items;
    Button AddCalories;
    Button addButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Recipes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recipes.
     */
    // TODO: Rename and change types and number of parameters
    public static Recipes newInstance(String param1, String param2) {
        Recipes fragment = new Recipes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipe_items = new ArrayList<Food>();
        View fragment = inflater.inflate(R.layout.fragment_recipes, container, false);

        Cursor cursor = getContext().getContentResolver().query(RecipesProvider.CONTENT_URI,null,null,null,null);
        if(cursor != null){
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    recipe_items.add(new Food(cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)));
                    cursor.moveToNext();
                }
            }
        }
        addButton = fragment.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRecipe addRecipe = new newRecipe();
                addRecipe.show(getActivity().getSupportFragmentManager(),"add recipes");
            }
        });
        AddCalories = fragment.findViewById(R.id.AddCalories);
        recipelistR = fragment.findViewById(R.id.recipe_listR);
        recipelistR.setLayoutManager(new LinearLayoutManager(getActivity()));
        foods = new RecipesAdapter(getContext(),recipe_items);
        recipelistR.setAdapter(foods);
        recipelistR.setItemAnimator(new DefaultItemAnimator());


        return fragment;
        //UseFoodAdapter to populate recipes from database
    }
}