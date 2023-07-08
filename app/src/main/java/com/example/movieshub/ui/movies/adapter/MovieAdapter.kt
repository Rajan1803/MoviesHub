package com.example.movieshub.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.data.response.Movie
import com.example.movieshub.databinding.ItemMovieBinding
import com.example.movieshub.databinding.ItemProgressLoadingBinding

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieList = ArrayList<Movie>()
    private var isLoading = false

    enum class MovieViewType {
        MOVIE, LOADING
    }

    inner class MovieViewHolder(private var binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }

    inner class LoadingViewHolder(private var binding: ItemProgressLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MovieViewType.values()[viewType]) {
            MovieViewType.MOVIE -> {
                MovieViewHolder(
                    ItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            MovieViewType.LOADING -> {
                LoadingViewHolder(
                    ItemProgressLoadingBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = if (isLoading) {
        movieList.size + 1
    } else {
        movieList.size
    }

    override fun getItemViewType(position: Int): Int =
        (if (isLoading && position == movieList.size) MovieViewType.LOADING
        else MovieViewType.MOVIE).ordinal

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(movieList[position])
        }
    }

    fun showLoading() {
        isLoading = true
        notifyItemInserted(movieList.size)
    }

    fun hideLoading() {
        isLoading = false
        notifyItemRemoved(movieList.size)
    }

    fun addList(list: ArrayList<Movie>) {
        movieList.addAll(list)
        notifyItemRangeInserted(movieList.size - list.size, list.size)
    }
}