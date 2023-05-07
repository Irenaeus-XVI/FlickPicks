package com.example.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Retrofit.PreferenceHandler;

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
    }

    public void openLink(View view) {
        String profile = view.getContentDescription().toString();
        String url = "https://github.com/" + profile;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}