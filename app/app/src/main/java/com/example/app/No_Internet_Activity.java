package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class No_Internet_Activity extends AppCompatActivity {

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