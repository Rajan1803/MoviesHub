package com.example.movieshub.ui.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieshub.ui.movies.adapter.MovieAdapter
import com.example.movieshub.databinding.FragmentMoviesBinding
import com.example.movieshub.utils.listeners.PaginationListener
import com.example.movieshub.ui.movies.respository.MoviesRepository
import com.example.movieshub.ui.movies.viewmodel.MoviesVM

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel by activityViewModels<MoviesVM> {
        MoviesVM.Factory(MoviesRepository())
    }
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setUpRecyclerView()
        setUpViewModel()
        viewModel.getMovies()
    }

    private fun setUpViewModel() {
        viewModel.apply {
            moviesLiveData.observe(viewLifecycleOwner) {
                movieAdapter.addList(it)
            }
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) movieAdapter.showLoading() else movieAdapter.hideLoading()
            }
            isFailure.observe(viewLifecycleOwner) { failureMessage ->
                Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvMovies.apply {
            adapter = movieAdapter
            val gridLayoutManager = GridLayoutManager(context, 3)
            layoutManager = gridLayoutManager
            addOnScrollListener(object : PaginationListener(gridLayoutManager) {
                override val isLastPage: Boolean
                    get() = viewModel.isLastPage
                override val isLoading: Boolean
                    get()  {
                        return viewModel.isLoading.value ?: false
                    }
                override fun loadMoreItems() {
                    viewModel.getMovies()
                }
            })
        }
    }
}