//foods fragment
package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment{
    RecyclerView foodList;
    FoodAdapter foods;
    ArrayList<Food> foodArrayList;
    Button info;
    Button save;
    EditText search;
    ImageFilterButton searchButton;
    ArrayList<String> name = new ArrayList<>();
    Food test1 = new Food("Pizza",25000,"5","3000","Place in oven","Cheese,sauce,dough","Fresh from the oven");
    Food test2 = new Food("Burger",1,"2","3","Place on grill","Burger,pickles(optional),lettuce,tomato","Great");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
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

        getContext().getContentResolver().delete(APIProvider.CONTENT_URI,null,null);
        View fragment = inflater.inflate(R.layout.fragment_food, container, false);
        foodArrayList = new ArrayList<>();
        searchButton = fragment.findViewById(R.id.searchButton);
        foodList = fragment.findViewById(R.id.recipe_listR);
        save = fragment.findViewById(R.id.save);

        search = fragment.findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodArrayList = new ArrayList<>();
                setRecyclerView();
                ArrayList<String> whatever = new ArrayList<>();

                OkHttpClient client = new OkHttpClient();

                String query = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/complexSearch?query=" + search.getText().toString().trim();

                Request request = new Request.Builder()
                        .url(query)
                        .get()
                        .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                        .addHeader("X-RapidAPI-Key", "ce27b5bf1amsh8a5d1b78ea7b1fdp11cbd0jsn8bf2176ffabb")
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String json = response.body().string();
                        ObjectMapper mapper = new ObjectMapper();
                        RecipeSearch rp = mapper.readValue(json, RecipeSearch.class);



                        for(int i=0;i<rp.results.size();i++)
                        {

                            ContentValues values = new ContentValues();
                            values.put("ID", rp.results.get(i).id);
                            values.put("NAME", rp.results.get(i).title);
                            getContext().getContentResolver().insert(APIProvider.CONTENT_URI, values);
                        }

                    }
                });

                int id;
                String title;
                Cursor mCursor = getContext().getContentResolver().query(APIProvider.CONTENT_URI,null,null,null,null);
                if(mCursor.getCount()>0) {
                    mCursor.moveToFirst();
                    id = mCursor.getInt(1);
                    title = mCursor.getString(2);


                    String ingredientQuery = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + id + "/ingredientWidget.json";
                    String nutritionQuery = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + id + "/nutritionWidget.json";
                    String instructionQuery = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + id + "/information";


                    Request ingredient_request = new Request.Builder()
                            .url(ingredientQuery)
                            .get()
                            .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                            .addHeader("X-RapidAPI-Key", "68bc5ae4f0msh53ad0da634b6af7p12ca67jsnc81333f2c6e9")
                            .build();

                    client.newCall(ingredient_request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ContentValues values = new ContentValues();
                            String json = response.body().string();
                            ObjectMapper mapper = new ObjectMapper();
                            IngredientSearch ingredientSearch = mapper.readValue(json, IngredientSearch.class);

                            String[] selectionArgs = {id + "", title};
                            String myingredients = "";
                            for (Ingredients ig : ingredientSearch.ingredients) {
                                myingredients += ig.name + " | ";
                            }

                            values.put("INGREDIENTS", myingredients);
                            getContext().getContentResolver().update(APIProvider.CONTENT_URI, values, "ID = ? AND NAME = ?", selectionArgs);
                        }
                    });


                    Request nutritionRequest = new Request.Builder()
                            .url(nutritionQuery)
                            .get()
                            .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                            .addHeader("X-RapidAPI-Key", "68bc5ae4f0msh53ad0da634b6af7p12ca67jsnc81333f2c6e9")
                            .build();

                    client.newCall(nutritionRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String json = response.body().string();
                            ObjectMapper mapper = new ObjectMapper();
                            Nutrition nutrition = mapper.readValue(json, Nutrition.class);
                            ContentValues values = new ContentValues();
                            values.put("PROTEIN", nutrition.protein);
                            values.put("CARBS", nutrition.carbs);
                            values.put("CALORIES", nutrition.calories.replaceAll("k", ""));
                            String[] selectionArgs = {id + "", title};
                            getContext().getContentResolver().update(APIProvider.CONTENT_URI, values, "ID = ? AND NAME = ?", selectionArgs);

                        }
                    });

                    Request instructionRequest = new Request.Builder()
                            .url(instructionQuery)
                            .get()
                            .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                            .addHeader("X-RapidAPI-Key", "68bc5ae4f0msh53ad0da634b6af7p12ca67jsnc81333f2c6e9")
                            .build();

                    client.newCall(instructionRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String json = response.body().string();
                            ObjectMapper mapper = new ObjectMapper();
                            Information info = mapper.readValue(json, Information.class);
                            ContentValues values = new ContentValues();
                            values.put("ADDITIONALINFO", info.summary);
                            values.put("SUMMARY", info.instructions);
                            String[] selectionArgs = {id + "", title};
                            getContext().getContentResolver().update(APIProvider.CONTENT_URI, values, "ID = ? AND NAME = ?", selectionArgs);
                        }
                    });

                    mCursor.moveToFirst();
                    Food test = new Food(mCursor.getString(2), mCursor.getInt(3), mCursor.getString(4), mCursor.getString(5), mCursor.getString(6), mCursor.getString(7), mCursor.getString(8));
                    foodArrayList.add(test);

                    foodArrayList.add(test1);
                    foodArrayList.add(test2);
                    setRecyclerView();

                }


                getContext().getContentResolver().delete(APIProvider.CONTENT_URI,null,null);
            }
        });
        setRecyclerView();





        //info.setOnClickListener(InfoClick);
        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(save.getText() == "Save"){
                    save.setText("Unsave");
                    //Save to RecipesProvider
                }else{
                    save.setText("Save");
                    //Unsave to RecipesProvider
                }
            }
        });*/

        return fragment;
    }

    void setRecyclerView()
    {
        foodList.setLayoutManager(new LinearLayoutManager(getActivity()));
        foods = new FoodAdapter(getContext(),foodArrayList);
        foodList.setAdapter(foods);
        foodList.setItemAnimator(new DefaultItemAnimator());
    }


   /*private View.OnClickListener InfoClick = new View.OnClickListener(){
        public void onClick(View v){
            FoodInfo summary = new FoodInfo();
            summary.show(getActivity().getSupportFragmentManager(), "Food Info");
        }
   };*/


}