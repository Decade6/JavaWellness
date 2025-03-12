/*
    Exercise class for exercises
 */
package com.example.myapplication;

import java.util.Date;

public class Exercise {
    String workout;
    int duration;
    double calorie;
    String type;
    String time;

    public Exercise(String workout, int duration, double calorie,String type, String time)
    {
        this.workout = workout;
        this.duration = duration;
        this.calorie = calorie;
        this.type = type;
        this.time = time;
    }

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
