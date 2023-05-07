package com.example.app.Model;


import com.example.app.Response.MovieResponse;
import com.example.app.Response.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvApiService {
    @GET("trending/tv/day")
    Call<TvResponse> getPopularMovies(@Query("api_key") String apiKey);
}
