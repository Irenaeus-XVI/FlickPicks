package com.example.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Retrofit.PreferenceHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContributionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrtibution);

        PreferenceHandler prefHandler = new PreferenceHandler(this);

        if (prefHandler.getEmail().equals("none")) {
            Intent intent = new Intent(ContributionActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_profile);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                switch (item.getItemId()) {
                    case R.id.nav_movie:
                        Intent profileintent = new Intent(ContributionActivity.this, MovieListActivity.class);
                        startActivity(profileintent);
                        break;
                    case R.id.nav_tv:
                        Intent tvIntent = new Intent(ContributionActivity.this, TvListActivity.class);
                        startActivity(tvIntent);
                        break;
                    case R.id.nav_profile:
                        // Handle profile click

                        break;
                }
                return true;
            }
        });


    }

    public void openLink(View view) {
        String profile = view.getContentDescription().toString();
        String url = "https://github.com/" + profile;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}