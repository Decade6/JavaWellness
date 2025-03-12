package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button logIn, createAccount;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        logIn = findViewById(R.id.login);
        createAccount = findViewById(R.id.createAccount);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int errorFlag = 0;
                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Please enter a valid Email");
                    errorFlag = 1;
                }
                if(email.getText().toString().isEmpty()) {
                    email.setError("Please enter a Email");
                    errorFlag = 1;
                }
                if(password.getText().toString().length() < 6) {
                    password.setError("Please enter a password atleast 6 characters long");
                    errorFlag = 1;
                }
                if(password.getText().toString().isEmpty()) {
                    password.setError("Please enter your password");
                    errorFlag = 1;
                }

                if(errorFlag == 1)
                    return;

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences mPref = getSharedPreferences("user_profile", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = mPref.edit();

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    edit.putBoolean("login",true);
                                    edit.commit();
                                    Intent intent = new Intent(Login.this, LaunchScreen.class);
                                    intent.putExtra("User", user);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login.this, "Error: Failed to Sign In", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int errorFlag = 0;
                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Enter a valid Email");
                    errorFlag = 1;
                }
                if(email.getText().toString().isEmpty()) {
                    email.setError("An Email is required");
                    errorFlag = 1;
                }
                if(password.getText().toString().length() < 6) {
                    password.setError("Password must be 6 characters long");
                    errorFlag = 1;
                }
                if(password.getText().toString().isEmpty()) {
                    password.setError("Enter a password");
                    errorFlag = 1;
                }
                if(errorFlag == 1)
                    return;

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    SharedPreferences mPref = getSharedPreferences("user_profile", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = mPref.edit();
                                    edit.putBoolean("login",true);
                                    edit.putString("email", email.getText().toString().trim());
                                    edit.commit();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(Login.this, LaunchScreen.class);
                                    intent.putExtra("User", user);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this, "Error: Failed to create account",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }
}