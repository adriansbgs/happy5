package com.ftw.happy5test.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftw.happy5test.databinding.ActivityMainBinding
import com.ftw.happy5test.model.Movies
import com.ftw.happy5test.utils.ResultState
import com.ftw.happy5test.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        movieViewModel.getAllMovies()
        movieViewModel.mutableResultState.observe(this, Observer { state ->
            stateResult(state)
        })
    }

    private fun stateResult(state: ResultState) {

        when (state) {
            is ResultState.Success<*> -> {
                binding.shimmer.stopShimmer()
                binding.shimmer.visibility = View.GONE
                binding.rvMovies.visibility = View.VISIBLE
                adapter = MoviesAdapter(state.data as List<Movies>)
                binding.rvMovies.also {
                    it.layoutManager = LinearLayoutManager(this)
                    it.adapter = adapter
                }
                adapter.setListener { movies ->
                    val movieId = movies.id
                    Intent(this@MainActivity, MovieDetailActivity::class.java).apply {
                        putExtra("movieId", movieId)
                        startActivity(this)
                    }
                }
            }
            is ResultState.Loading -> {
                binding.shimmer.startShimmer()
            }
            is ResultState.Error -> {
                binding.shimmer.stopShimmer()
                binding.shimmer.visibility = View.GONE
                Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.shimmer.startShimmer()
    }

    override fun onStop() {
        super.onStop()
        binding.shimmer.stopShimmer()
    }
}