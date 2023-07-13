package com.example.movieshub.api

import com.example.movieshub.data.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("language") language: String, @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("tv/popular")
    suspend fun getTVShowsList(
        @Query("language") language: String, @Query("page") page: Int
    ): Response<MovieResponse>
}