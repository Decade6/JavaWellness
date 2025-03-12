package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class LaunchScreen extends AppCompatActivity implements AddExercise.OnDialogCloseListener, AddShoppingItem.OnShoppingListCloseListener {
    BottomNavigationView bottomNavigationView;
    FoodFragment foods = new FoodFragment();
    Recipes recipes = new Recipes();
    Settings settings = new Settings();
    profile profile = new profile();
    fitness fitness = new fitness();
    shoppinglist shoppinglist = new shoppinglist();
    SharedPreferences pref = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);



        long timeInMillis = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(timeInMillis);

        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        long hour = 23 - now.get(Calendar.HOUR_OF_DAY);
        long minute = 59 - now.get(Calendar.MINUTE);
        long second = 59 - now.get(Calendar.MINUTE);

        System.out.println(hour);
        System.out.println(minute);
        System.out.println(second);

        long timedifference = (hour*60*60) + (minute*60) + second;
        System.out.println("Time: " + timedifference);



        Constraints constraints = new Constraints.Builder().build();
        PeriodicWorkRequest clearDataRequest = new PeriodicWorkRequest.Builder(DatabaseManager.class,1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(timedifference,TimeUnit.SECONDS).build();

        pref = getSharedPreferences("user_profile", MODE_PRIVATE);
        boolean open = pref.getBoolean("OPENED",false);
        if (pref.getBoolean("firstrun", true)) {
            WorkManager.getInstance(this).enqueue(clearDataRequest);
            pref.edit().putBoolean("firstrun", false).commit();
        }



         bottomNavigationView = findViewById(R.id.navigation);
         getSupportFragmentManager().beginTransaction().replace(R.id.nav,foods).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.food:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav,foods).commit();
                        return true;
                    case R.id.recipes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav,recipes).commit();
                        return true;
                    case R.id.settings:
                        SharedPreferences mPref = getSharedPreferences("user_profile", MODE_PRIVATE);
                        boolean profileExists = mPref.getBoolean("profile_saved", false);
                        if(profileExists)
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav, profile).commit();
                        else
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav,settings).commit();
                        return true;
                    case R.id.fitness:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav,fitness).commit();
                        return true;
                    case R.id.shopping_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav,shoppinglist).commit();
                        return true;

                }

                return false;
            }
        });
    }

    @Override
    public void onDialogClose(String name, int duration, double calories, String time, String type) {
        fitness.onDialogClose(name,duration,calories, time, type);
    }

    @Override
    public void onShoppingClose(String name, String info, int quantity) {
        shoppinglist.onShoppingClose(name,info,quantity);
    }
}