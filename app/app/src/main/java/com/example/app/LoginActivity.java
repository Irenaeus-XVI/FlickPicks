package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Sends you to Register page when you press on Register button
        Button registerButton = (Button) findViewById(R.id.gotoReg_button);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Checks your credentials when you press on Login button
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            // Get the username and password from the text fields
            String username = ((EditText) findViewById(R.id.email_in)).getText().toString();
            String password = ((EditText) findViewById(R.id.pw_in)).getText().toString();

            // Get the hashed password from the database
            String hashedPassword = "hashedPassword";

            // Check if the password is correct
            if (BCrypt.checkpw(password, hashedPassword)) {
                // If the password is correct, send you to the MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                // If the password is incorrect, print an error message
                Log.d("LoginActivity", "Incorrect password");
            }
        });


    }
}