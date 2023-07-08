package com.example.movieshub.network

import com.example.movieshub.data.model.response.CastDetail
import com.example.movieshub.data.model.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiInterface {
    @GET("3/movie/{category}")
    suspend fun getMovies(
        @Header("Authorization") key: String,
        @Path(value = "category") category: String
    ): Response<MovieResponse>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCast(
        @Header("Authorization") key: String,
        @Path(value = "movie_id") movieId: Int
    ): Response<CastDetail>
}