/*
    fitness fragment
 */
package com.example.myapplication;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fitness#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fitness extends Fragment implements AddExercise.OnDialogCloseListener {


    double calorie_goal;
    double exerciseCalories = 0;
    double foodCalories;
    double remainingCalories = 0;

    TextView goal;
    TextView remainingCalorie;
    TextView exerciseCalorie;
    TextView totalCals;
    TextView foodCalorie;
    ProgressBar progressCount;


    RecyclerView exerciseList;
    ExerciseAdapter exercises;
    Button addExercise;
    ArrayList<Exercise> exercise_items;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fitness() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fitness.
     */
    // TODO: Rename and change types and number of parameters
    public static fitness newInstance(String param1, String param2) {
        fitness fragment = new fitness();
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
    public void onResume() {
        SharedPreferences mPref = getContext().getSharedPreferences("user_profile", getContext().MODE_PRIVATE);
        calorie_goal = mPref.getInt("goal", 2000);
        foodCalories = mPref.getFloat("food_calories", 0);
        remainingCalories = calorie_goal - foodCalories + exerciseCalories;
        int progress = (int)(((calorie_goal - remainingCalories)/calorie_goal) * 100);
        updateProgress(progress);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_fitness, container, false);
        goal = fragment.findViewById(R.id.goalCalorie);
        remainingCalorie = fragment.findViewById(R.id.remainingCalorie);
        progressCount = fragment.findViewById(R.id.calorieCount);
        addExercise = fragment.findViewById(R.id.addExercise);
        exerciseCalorie = fragment.findViewById(R.id.exerciseCalorie);
        foodCalorie = fragment.findViewById(R.id.foodCalorie);
        totalCals = fragment.findViewById(R.id.totalCals);
        exercise_items = new ArrayList<Exercise>();

        SharedPreferences mPref = getContext().getSharedPreferences("user_profile", getContext().MODE_PRIVATE);
        calorie_goal = mPref.getInt("goal", 2000);
        foodCalories = mPref.getFloat("food_calories", 0);
        exerciseCalories = 0;
        remainingCalories = 0;



        //retrieve data from content provider
        Cursor mCursor = getContext().getContentResolver().query(ExerciseProvider.CONTENT_URI,null,null,null,null);
        if(mCursor != null)
        {
            if(mCursor.getCount()>0){
                mCursor.moveToFirst();
                while(!mCursor.isAfterLast())
                {
                    exercise_items.add(new Exercise(mCursor.getString(1), mCursor.getInt(3), mCursor.getDouble(2), mCursor.getString(5), mCursor.getString(4)));
                    exerciseCalories += mCursor.getDouble(2);
                    mCursor.moveToNext();
                }
                mCursor.moveToFirst();
            }
        }

        remainingCalories = calorie_goal - foodCalories + exerciseCalories;
        int progress = (int)(((calorie_goal - remainingCalories)/calorie_goal) * 100);

        exerciseList = fragment.findViewById(R.id.exerciseList);

        exerciseCalorie.setText((int)exerciseCalories + "");
        goal.setText((int)calorie_goal + "");
        totalCals.setText((int)calorie_goal + "");
        foodCalorie.setText("-" + (int)foodCalories);
        exerciseCalorie.setText("+" + (int)exerciseCalories);
        remainingCalorie.setText((int)remainingCalories + "");

        updateProgress(progress);



        setRecyclerView();

        //set progress base on the calories eaten and taken out
        //to be implemented when calorie count is ready


        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExercise addExercise = new AddExercise();
                addExercise.show(getActivity().getSupportFragmentManager(), "add exercise");
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Exercise item = exercise_items.get(viewHolder.getAdapterPosition());
                exercise_items.remove(viewHolder.getAdapterPosition());
                exercises.notifyDataSetChanged();

                String selectionClause = "description = ? AND duration = ? AND calories = ? AND time = ? AND type = ?";
                String[] selection = { item.getWorkout(),item.getDuration()+"",item.getCalorie()+"",item.getTime(), item.getType() };
                getContext().getContentResolver().delete(ExerciseProvider.CONTENT_URI,selectionClause, selection);
                Snackbar snackbar = Snackbar.make(getView(),"Item Removed", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(exerciseList);
        // Inflate the layout for this fragment
        return fragment;
    }

    private void setRecyclerView(){
        exerciseList.setLayoutManager(new LinearLayoutManager(getActivity()));
        exercises = new ExerciseAdapter(getActivity(), exercise_items);
        exerciseList.setAdapter(exercises);
        exerciseList.setItemAnimator(new DefaultItemAnimator());
    }

    private void updateProgress(int progress){
        if(progress > 100) {
            progress = 100;
        }

        progressCount.setProgress(progress);
    }

    @Override
    public void onDialogClose(String name, int duration, double calories, String time, String type) {
        remainingCalories += calories;
        exerciseCalories += calories;
        exerciseCalorie.setText("+" + exerciseCalories);
        remainingCalorie.setText(remainingCalories + "");
        exercise_items.add(new Exercise(name, duration, calories, time,type));
        updateProgress((int)(((calorie_goal - remainingCalories)/calorie_goal) * 100));
        setRecyclerView();
    }
}