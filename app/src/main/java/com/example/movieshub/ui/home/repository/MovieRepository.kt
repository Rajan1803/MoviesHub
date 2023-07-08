package com.example.movieshub.ui.home.repository

import com.example.movieshub.data.model.response.CastDetail
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.network.ApiClient
import com.example.movieshub.util.WebServiceUtil
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MovieRepository {
    suspend fun getMovies(category: String): MovieResponse? {
        val response = ApiClient.apiService.getMovies(WebServiceUtil.TOKEN, category = category)
        return response.body()

    }
    suspend fun getCast(movieID: Int): CastDetail? {
        val castResponse = ApiClient.apiService.getCast(WebServiceUtil.TOKEN, movieID)
        return castResponse.body()
    }
    fun getCategories(): List<String> {
        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(WebServiceUtil.CATEGORYURL)
            .addHeader("Authorization", WebServiceUtil.TOKEN)
            .build()
        val listOfCategories = ArrayList<String>()
        val response = okHttpClient.newCall(request).execute()
        val jsonString = response.body?.string()

        if (jsonString != null) {
            listOfCategories.addAll(parseGenres(jsonString))
        }
        return listOfCategories

    }

    private fun parseGenres(json: String): List<String> {

        val genreNames = mutableListOf<String>()
        val jsonObject = JSONObject(json)
        val genresArray = jsonObject.getJSONArray("genres")

        for (i in 0 until genresArray.length()) {
            val genreObject = genresArray.getJSONObject(i)
            val genreName = genreObject.getString("name")
            genreNames.add(genreName)
        }
        return genreNames
    }

}