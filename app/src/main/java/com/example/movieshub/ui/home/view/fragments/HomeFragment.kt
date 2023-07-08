package com.example.movieshub.ui.home.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.movieshub.R
import com.example.movieshub.databinding.FragmentHomeBinding
import com.example.movieshub.ui.home.view.adapter.MovieAdapter
import com.example.movieshub.ui.home.view.adapter.MoviePagerAdapter
import com.example.movieshub.ui.home.viewmodel.HomeViewModel
import com.example.movieshub.util.NetworkStateViewModel
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var popularMovieAdapter = MovieAdapter()
    private var inTheatresMovieAdapter = MovieAdapter()
    private var moviePagerAdapter = MoviePagerAdapter()
    private var listOfCategories = ArrayList<String>()
    private lateinit var networkStateViewModel: NetworkStateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setUpViewModel()
        binding.viewPager2.adapter = moviePagerAdapter
        val itemDecoration = object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.apply {
                    left = 16
                    right = 16
                }
            }
        }

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(itemDecoration)
            adapter = popularMovieAdapter
        }

        binding.rvTheatre.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(itemDecoration)
            adapter = inTheatresMovieAdapter
        }
        checkNetwork()
    }

    private fun setUpViewModel() {

        networkStateViewModel = ViewModelProvider(this)[NetworkStateViewModel::class.java]
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.apply {
            popularMovies.observe(viewLifecycleOwner) { movies ->
                if (movies != null) {
                    popularMovieAdapter.submitList(movies.moviesList)
                }
            }

            inTheatresMovie.observe(viewLifecycleOwner) { movies ->
                if (movies != null) {
                    inTheatresMovieAdapter.submitList(movies.moviesList)
                    moviePagerAdapter.submitList(movies.moviesList)
                }
                binding.progressBar.visibility = View.GONE
            }

            categoryData.observe(viewLifecycleOwner) { categories ->
                listOfCategories.addAll(categories)
            }

        }

        networkStateViewModel.apply {
            networkState.observe(viewLifecycleOwner) { network ->
                manageNetworkStates(network)
            }
            getNetworkUpdate()
        }
    }

    private fun manageNetworkStates(network: Boolean) {
        if (network) {
            setViewsOnNetworkAvailable()
        } else {
            setViewsOnNetworkUnavailable()
        }
    }

    private fun checkNetwork() {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetwork
        if (netInfo == null) {
            binding.apply {
                progressBar.visibility = View.GONE
                group.visibility = View.GONE
                txtvNoInternet.visibility = View.VISIBLE
            }
        } else {
            setViewsOnNetworkAvailable()
        }
    }

    private fun setViewsOnNetworkUnavailable() {
        binding.apply {
            group.visibility = View.GONE
            txtvNoInternet.visibility = View.VISIBLE
        }
        val snackBar = context?.let { context ->
            Snackbar.make(
                binding.root,
                context.getString(R.string.no_internet_connection),
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).setTextColor(Color.WHITE)
        }
        val sbView = snackBar?.view
        sbView?.setBackgroundColor(Color.BLACK)
        snackBar?.show()

    }

    private fun setViewsOnNetworkAvailable() {
        binding.apply {
            group.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            txtvNoInternet.visibility = View.GONE
        }
        val snackBar =
            Snackbar.make(binding.root, getString(R.string.online), Snackbar.LENGTH_LONG).setAction("Action", null)
                .setTextColor(Color.WHITE)
        val sbView = snackBar.view
        sbView.setBackgroundColor(Color.BLACK)
        snackBar.show()
        viewModel.apply {
            getPopularMovieList()
            getInTheatresMovies()
            getCategoriesDetail()
        }
    }

}