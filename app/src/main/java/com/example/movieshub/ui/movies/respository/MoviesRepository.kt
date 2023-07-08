package com.example.movieshub.ui.movies.respository

import android.util.Log
import com.example.movieshub.api.RetrofitClient
import com.example.movieshub.data.response.MovieResponse
import com.example.movieshub.utils.base.BaseRepository

class MoviesRepository: BaseRepository() {
    suspend fun getMovies(currentPage: Int): Result<MovieResponse> {
        return try {
            val response =
                RetrofitClient.service.getMoviesList(language = "en-US", page = currentPage)
            handleResponse(response)
        } catch (e: Exception) {
            Log.d("TAG", "getMovies: ${e.localizedMessage}")
            Result.failure(Throwable(e.localizedMessage))
        }
    }
}