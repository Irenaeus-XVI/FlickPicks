package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler(this);
    String salt = BCrypt.gensalt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button submitButton = (Button) findViewById(R.id.submitReg_button);
        submitButton.setOnClickListener(view -> {
            // Check the credentials if it's valid then move on otherwise print an error message
            String username = ((EditText) findViewById(R.id.username_reg)).getText().toString();
            String email = ((EditText) findViewById(R.id.email_reg)).getText().toString();
            String password = ((EditText) findViewById(R.id.pw_reg)).getText().toString();
            String passwordConfirm = ((EditText) findViewById(R.id.pwConfirm_reg)).getText().toString();

            // Check if the credentials are valid
            if (!checkCredentials(username, email, password, passwordConfirm))
                return;

            // Hash the password
            String hashedPassword = BCrypt.hashpw(password, salt);

            // Create a new User
            User user = new User(username, email, hashedPassword);

            // Store the User in the database
            storeCredentials(user);
            Log.d("RegisterActivity", "onCreate: " + user.getHashedPW());

            // Sends you to the LoginActivity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private boolean checkCredentials(String username, String email, String pw, String pwConfirm) {
        // Checks if the credentials are valid to be set in the database
        if (username.length() > 20 || username.length() < 4) {
            // If the username is too long or too short, print an error message
            Toast.makeText(this, "Username must be between 4 and 20 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pw.length() > 20 || pw.length() < 4) {
            // If the password is too long or too short, print an error message
            Toast.makeText(this, "Password must be between 4 and 20 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pw.equals(pwConfirm)) {
            // If the password and the password confirmation don't match, print an error message
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length() > 50 || email.length() < 14) {
            // If the email is too long or too short, print an error message
            Toast.makeText(this, "Email must be between 14 and 50 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        String emailPattern = "@gmail.com";
        boolean patternMatching = false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@' && i + 10 == email.length())
                patternMatching = true;
        }
        // If the email is not a gmail, print an error message
        if (!patternMatching) {
            Toast.makeText(this, "Email must be a gmail", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (db.getUser(email) != null) {
            // If the email is already in the database, print an error message
            Toast.makeText(this, "Email already used", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void storeCredentials(User user) {
        // Stores the User in the database
        db.addUser(user);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        // Emptying the text fields before navigating to login
        ((EditText) findViewById(R.id.username_reg)).setText("");
        ((EditText) findViewById(R.id.email_reg)).setText("");
        ((EditText) findViewById(R.id.pw_reg)).setText("");
        ((EditText) findViewById(R.id.pwConfirm_reg)).setText("");
    }
}