package com.rizkyfadillah.popularmoviesstage1.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class MovieResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("vote_average")
    public double voteAverage;

}
