package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.Adapters.TvAdapter;
import com.example.app.Model.Tv;
import com.example.app.Model.TvApiService;
import com.example.app.Response.TvResponse;
import com.example.app.Retrofit.PreferenceHandler;
import com.example.app.Retrofit.RetrofitClientInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvListActivity extends AppCompatActivity {

    private RecyclerView tvRecyclerView;
    private List<Tv> tvList;
    private TvAdapter tvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_list);

        PreferenceHandler prefHandler = new PreferenceHandler(this);

        if (prefHandler.getEmail().equals("none")) {
            Intent intent = new Intent(TvListActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        tvRecyclerView = findViewById(R.id.tvRecyclerView);
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvList = new ArrayList<>();
        tvAdapter = new TvAdapter(this, tvList);
        tvRecyclerView.setAdapter(tvAdapter);

        getPopularMovies();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_search);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent movieIntent = new Intent(TvListActivity.this, MovieListActivity.class);
                        startActivity(movieIntent);
                        break;
                    case R.id.nav_search:
                        break;
                    case R.id.nav_profile:
                        // Handle profile click
                        Intent profileintent = new Intent(TvListActivity.this, ProfileActivity.class);
                        startActivity(profileintent);
                        break;
                }
                return true;
            }
        });
    }

    private void getPopularMovies() {
        TvApiService tvApiService = RetrofitClientInstance.getRetrofitInstance().create(TvApiService.class);

        Call<TvResponse> call = tvApiService.getPopularMovies(getString(R.string.api_key));
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvList.addAll(response.body().getResults());
                    tvAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TvListActivity.this, "Failed to get TV shows", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Toast.makeText(TvListActivity.this, "Failed to get TV shows", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
