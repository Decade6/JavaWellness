/**
 * Add exercise for dialog fragment
 * to add exercise to the fitness page
 */
package com.example.myapplication;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class AddExercise extends DialogFragment {
    EditText workout;
    EditText calories;
    EditText duration;
    EditText time;
    Spinner amPm;
    ImageButton timePicker;
    Spinner type;

    private OnDialogCloseListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnDialogCloseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDialogCloseListener");
        }
    }

    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            time.setText(i%12 + ":" + i1);
            if(i>=12)
                amPm.setSelection(1);
            else
                amPm.setSelection(0);
        }
    };

    public interface OnDialogCloseListener{
        void onDialogClose(String name, int duration, double calories, String time, String type);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_exercise, null);

        workout = view.findViewById(R.id.ITitle);
        calories = view.findViewById(R.id.ICalories);
        duration = view.findViewById(R.id.IIngredients);
        time = view.findViewById(R.id.timeWorkout);
        amPm = view.findViewById(R.id.spinner);
        timePicker = view.findViewById(R.id.timePicker);
        type = view.findViewById(R.id.workoutType);

        builder.setView(view)
                .setTitle("Add Exercise")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContentValues mValues = new ContentValues();

                        mValues.put("description", workout.getText().toString().trim());

                        try{
                            mValues.put("calories", Double.parseDouble(calories.getText().toString().trim()));
                        } catch (NumberFormatException e) {
                            // catches error if string is entered
                        }
                        try{
                            mValues.put("duration", Integer.parseInt(duration.getText().toString().trim()));
                        } catch (NumberFormatException e) {
                            // catches error if string is entered
                        }
                        mValues.put("time", time.getText().toString().trim());  //to be fixed later
                        mValues.put("type", type.getSelectedItem().toString());

                        getContext().getContentResolver().insert(ExerciseProvider.CONTENT_URI, mValues);

                        int val = 0;
                        double val1 = 0;

                        try {
                            val = Integer.parseInt(duration.getText().toString().trim());
                        } catch (Exception e){
                            // catches
                        }

                        try {
                            val1 = Double.parseDouble(calories.getText().toString().trim());
                        } catch (Exception e ){
                            // catches
                        }

                        listener.onDialogClose(workout.getText().toString().trim(), val, val1, time.getText().toString().trim(),type.getSelectedItem().toString());

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, timeListener, hour, minutes, false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        return builder.create();
    }
}
