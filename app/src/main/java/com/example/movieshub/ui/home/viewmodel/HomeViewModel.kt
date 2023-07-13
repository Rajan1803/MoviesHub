package com.example.movieshub.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.ui.home.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository()
    private val popularMoviesLiveData = MutableLiveData<MovieResponse?>()
    val popularMovies: LiveData<MovieResponse?>
        get() = popularMoviesLiveData

    private val inTheatresMoviesList = MutableLiveData<MovieResponse?>()
    val inTheatresMovie: LiveData<MovieResponse?>
        get() = inTheatresMoviesList

    private val categoryLiveData = MutableLiveData<List<String>>()
    val categoryData: MutableLiveData<List<String>>
        get() = categoryLiveData

    fun getPopularMovieList() {
        viewModelScope.launch {
            val movieResponse = repository.getMovies("popular")
            popularMoviesLiveData.postValue(movieResponse)
        }
    }

    fun getInTheatresMovies() {
        viewModelScope.launch {
            val movieResponse = repository.getMovies("now_playing")
            inTheatresMoviesList.postValue(movieResponse)
        }
    }

    fun getCategoriesDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryLiveData.postValue(repository.getCategories())
        }
    }

}