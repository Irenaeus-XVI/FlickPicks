package com.example.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}