/*
    Exercise Adapter for Exercise Recycler View
 */
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{

    Context context;
    ArrayList<Exercise> exerciseList;

    public ExerciseAdapter(Context context, ArrayList<Exercise> exerciseList)
    {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String workout = exerciseList.get(position).getWorkout();
        int duration = exerciseList.get(position).getDuration();
        double calories_burned= exerciseList.get(position).getCalorie();

        holder.description.setText(workout);
        holder.duration.setText(duration + " minutes");
        holder.calories.setText(calories_burned + "");
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        LinearLayout mylayout;
        TextView description;
        TextView duration;
        TextView calories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mylayout = itemView.findViewById(R.id.linearLayout);
            description = itemView.findViewById(R.id.workoutDescription);
            duration = itemView.findViewById(R.id.duration);
            calories = itemView.findViewById(R.id.calories);
        }

    }

}
