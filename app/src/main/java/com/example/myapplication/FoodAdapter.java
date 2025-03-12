//Food list adapter for the recylerview
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    ArrayList<Food> foodArrayList;


    public FoodAdapter(Context context, ArrayList<Food> foodArrayList){
        this.context = context;
        this.foodArrayList = foodArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup holder, int type){
        View view = LayoutInflater.from(holder.getContext()).inflate(R.layout.food_item, holder, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos){
        boolean foodExists = false;
        String food_item = foodArrayList.get(pos).getTitle();
        int calories = foodArrayList.get(pos).getCalories();
        String ingredients = foodArrayList.get(pos).getIngredients();
        String carbs = foodArrayList.get(pos).getCarbs();
        String summary = foodArrayList.get(pos).getSummary();
        String protein = foodArrayList.get(pos).getProtein();
        String additionalInfo = foodArrayList.get(pos).getAdditionalInfo();



        holder.title.setText(food_item);
        holder.calories.setText("Calories: " + calories );
        holder.Ingredients.setText("Ingredients: " + ingredients);
        holder.Carbs.setText("Carbs: " + carbs);

        Cursor cursor = context.getContentResolver().query(RecipesProvider.CONTENT_URI,null,null,null,null);
        if(cursor != null){
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    Food food = new Food(cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
                    if(food.equals(new Food(food_item,calories,carbs,protein,additionalInfo,ingredients,summary)))
                        foodExists = true;
                    cursor.moveToNext();
                }
            }
        }

        if(foodExists)
            holder.save.setText("UNSAVE");

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues mValues = new ContentValues();

                mValues.put("Title", food_item);
                mValues.put("Calories", calories);
                mValues.put("Carbs", carbs);
                mValues.put("Protein", protein);
                mValues.put("AdditionalInfo", additionalInfo);
                mValues.put("Ingredients", ingredients);
                mValues.put("Summary",summary);



                if(holder.save.getText().toString().equalsIgnoreCase("SAVE")){
                    //Save to recipes
                    holder.save.setText("UNSAVE");
                    context.getContentResolver().insert(RecipesProvider.CONTENT_URI, mValues);
                }else{
                    holder.save.setText("SAVE");
                    //Delete from recipes
                    String[] selectionArgs = {food_item,calories+"",carbs,protein, additionalInfo,ingredients,summary};
                    context.getContentResolver().delete(RecipesProvider.CONTENT_URI, "Title = ?  AND Calories = ?  AND Carbs = ? AND Protein = ?  AND AdditionalInfo = ?  AND Ingredients = ?  AND Summary = ? ", selectionArgs);


                }
            }
        });

        holder.additionalinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FoodInfo info = new FoodInfo(foodArrayList.get(position).getTitle(),foodArrayList.get(position).getCalories(),foodArrayList.get(position).getIngredients(),foodArrayList.get(position).getCarbs(),foodArrayList.get(position).getSummary());
                info.show(((LaunchScreen)context).getSupportFragmentManager().beginTransaction(), "food info");
            }
        });
        /*
        holder.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                IngredientsApi apiInstance = new IngredientsApi();
                try{
                    List<InLineResponse20024> result = apiInstance.autoCompleteIngredientsSearch();
                    System.out.println(result);

                }catch (ApiException e){
                    System.out.println("Error with API Call");
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });*/

    }



    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView calories;
        TextView Ingredients;
        TextView Carbs;
        Button additionalinfo;
        Button save;
        SearchView search;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.recipes_titleR);
            calories = itemView.findViewById(R.id.caloriesTextR);
            Ingredients = itemView.findViewById(R.id.ingrediantsTextR);
            Carbs = itemView.findViewById(R.id.carbsTextR);
            additionalinfo = itemView.findViewById(R.id.additionalinfoR);
            save = itemView.findViewById(R.id.save);
            search = itemView.findViewById(R.id.search);


        }
    }

}

