package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.app.Adapters.MovieAdapter;
import com.example.app.Model.Movie;
import com.example.app.Model.MovieApiService;
import com.example.app.Response.MovieResponse;
import com.example.app.Retrofit.PreferenceHandler;
import com.example.app.Retrofit.RetrofitClientInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {


    private RecyclerView movieRecyclerView;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        PreferenceHandler prefHandler = new PreferenceHandler(this);

        // If the user is logged out he can't go to the profile page
        if (prefHandler.getEmail().equals("none")) {
            Intent intent = new Intent(MovieListActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        movieRecyclerView = findViewById(R.id.movieRecyclerView);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);
        movieRecyclerView.setAdapter(movieAdapter);

        getPopularMovies();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_search:
                        Intent tvIntent = new Intent(MovieListActivity.this, TvListActivity.class);
                        startActivity(tvIntent);
                        break;
                    case R.id.nav_profile:
                        // Handle profile click
                        Intent profileintent = new Intent(MovieListActivity.this,ProfileActivity.class);
                        startActivity(profileintent);
                        break;
                }
                return true;
            }
        });
    }




    private void getPopularMovies() {
        MovieApiService movieApiService = RetrofitClientInstance.getRetrofitInstance().create(MovieApiService.class);

        Call<MovieResponse> call = movieApiService.getPopularMovies(getString(R.string.api_key));
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList.addAll(response.body().getResults());
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MovieListActivity.this, "Failed to get movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieListActivity.this, "Failed to get movies", Toast.LENGTH_SHORT).show();
            }
        });

    }
}