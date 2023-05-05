package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.app.Adapters.MovieAdapter;
import com.example.app.Model.Movie;
import com.example.app.Model.MovieApiService;
import com.example.app.Response.MovieResponse;
import com.example.app.Retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieList extends AppCompatActivity {


    private RecyclerView movieRecyclerView;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieRecyclerView = findViewById(R.id.movieRecyclerView);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList);
        movieRecyclerView.setAdapter(movieAdapter);

        getPopularMovies();
    }

    private void getPopularMovies() {
        MovieApiService movieApiService = RetrofitClientInstance.getRetrofitInstance().create(MovieApiService.class);

        Call<MovieResponse> call = movieApiService.getPopularMovies(getString(R.string.api_key));
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    movieList.addAll(response.body().getResults());
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MovieList.this, "Failed to get movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieList.this, "Failed to get movies", Toast.LENGTH_SHORT).show();
            }
        });

    }
}