package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    FirebaseUser user;
    String uid;
    FirebaseDatabase database;
    DatabaseReference users;

    private AppCompatButton enter_button, more_info_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        enter_button = (AppCompatButton) findViewById(R.id.enter_button);

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)    {
                SharedPreferences mPref = getSharedPreferences("user_profile", MODE_PRIVATE);
                boolean loggedin = mPref.getBoolean("login", false);
                if(!loggedin) {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, LaunchScreen.class);
                    startActivity(intent);
                }
            }
        });

        more_info_button = (AppCompatButton) findViewById(R.id.more_info_button);

        more_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)    {
                Intent intent = new Intent(MainActivity.this, moreinfo.class);
                startActivity(intent);
            }
        });
    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        else {
            user = currentUser;
            uid = user.getUid();
            database = FirebaseDatabase.getInstance();
            users = database.getReference("users");
        }
    }


}