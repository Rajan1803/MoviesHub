package com.example.movieshub.data.model.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("results")
    val moviesList: List<Movie> = listOf(),

    @SerializedName("total_pages")
    val totalPages: Int = 0,

    @SerializedName("total_results")
    val totalResults: Int = 0
)