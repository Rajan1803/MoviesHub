package com.example.movieshub.activities

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.movieshub.data.model.response.Movie
import com.example.movieshub.databinding.ActivityMoviesDetailBinding
import com.example.movieshub.ui.moviedetail.adapter.CastRecyclerAdapter
import com.example.movieshub.ui.moviedetail.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.launch

class MoviesDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoviesDetailBinding
    private lateinit var viewModel: MovieDetailViewModel
    private val castAdapter = CastRecyclerAdapter()

    companion object {
        private const val Movie_TEXT = "movie"
        fun getIntent(context: Context, movie: Movie): Intent =
            Intent(context, MoviesDetailActivity::class.java).apply {
                putExtra(Movie_TEXT, movie)
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Movie_TEXT, Movie::class.java)
        } else {
            intent.getParcelableExtra(Movie_TEXT)
        }

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        lifecycleScope.launch {
            viewModel.getCastDetail(movie?.id ?: 0)
        }
        binding.movie = movie
        viewModel.castData.observe(this) { castData ->
            if (castData != null) {
                castAdapter.submitList(castData)
            }
        }
        val itemDecoration = object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.apply {
                    left = 10
                    right = 10
                    top = 10
                    bottom = 10
                }
            }
        }
        binding.rvCast.apply {
            addItemDecoration(itemDecoration)
            layoutManager = GridLayoutManager(this@MoviesDetailActivity, 4)
            adapter = castAdapter
        }
    }

}