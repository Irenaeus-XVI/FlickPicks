package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.Retrofit.PreferenceHandler;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    PreferenceHandler prefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHandler = new DatabaseHandler(this);
        prefHandler = new PreferenceHandler(this);
        // Sends you to Register page when you press on Register button
        Button registerButton =  findViewById(R.id.gotoReg_button);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Checks your credentials when you press on Login button
        Button loginButton =  findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            // Get the email and password from the text fields
            String email = ((EditText) findViewById(R.id.email_in)).getText().toString();
            String password = ((EditText) findViewById(R.id.pw_in)).getText().toString();

            User user = dbHandler.getUser(email);
            if (!checkCredentials(email, password) || user == null)
                return;

            // Get the hashed password from the database
            String hashedPassword = user.getHashedPW();

//             Check if the password is correct
            if (BCrypt.checkpw(password, hashedPassword)) {
//                 If the password is correct, send you to the Homepage
                loginSuccess(user);
            } else {
                // If the password is incorrect, print an error message
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public boolean checkCredentials(String email, String password) {

        // Check if email is valid and not a very large input to not lag the app
        if (email.length() > 50 || email.length() < 4) {
            Toast.makeText(this, "Email must be between 4 and 50 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Same thing for password
        if (password.length() > 20 || password.length() < 4) {
            Toast.makeText(this, "Password must be between 4 and 20 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Check if the email is in the database
        if (dbHandler.getUser(email) == null) {
            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginSuccess(User user) {
        Toast.makeText(this, "Login succeeded", Toast.LENGTH_SHORT).show();

        // Emptying the text fields before navigating to main activity
        ((EditText) findViewById(R.id.email_in)).setText("");
        ((EditText) findViewById(R.id.pw_in)).setText("");

        // Saving the user's email in the shared preferences
        prefHandler.setEmail(user.getEmail());

        // Navigating to main activity
        Intent intent = new Intent(LoginActivity.this, MovieListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}