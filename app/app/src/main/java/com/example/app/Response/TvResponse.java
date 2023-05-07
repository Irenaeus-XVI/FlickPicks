package com.example.app.Response;

import com.example.app.Model.Tv;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Tv> results;

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Tv> getResults() {
        return results;
    }
}
