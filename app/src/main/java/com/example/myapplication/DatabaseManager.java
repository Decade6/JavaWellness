/*
    Background process for deleting local database every 24 hours at 12am midnight
 */
package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class DatabaseManager extends Worker {
    public DatabaseManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        getApplicationContext().getContentResolver().delete(ExerciseProvider.CONTENT_URI,null,null);
        SharedPreferences mPref = getApplicationContext().getSharedPreferences("user_profile", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor edit = mPref.edit();
        edit.putFloat("food_calories", 0);
        edit.commit();
        return Result.success();
    }
}
