package com.example.app.Model;

import com.example.app.Response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("trending/movie/week")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);
}
