package com.ftw.happy5test.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val movieData: MutableList<Movies> = mutableListOf()

        movieViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        movieViewModel.getAllMovies()
        movieViewModel.mutableResultState.observe(this, Observer { state ->
            when (state) {
                is ResultState.Success<*> -> {
                    movieData.addAll(state.data as List<Movies>)
                    adapter = MoviesAdapter(movieData)
                    Log.d("MainActivity", "onCreate: $movieData")
                    binding.rvMovies.also {
                        it.layoutManager = LinearLayoutManager(this)
                        it.adapter = adapter
                    }
                    adapter.setListener { movies ->
                        val movieId = movies.id
                        Intent(this@MainActivity, MovieDetail::class.java).apply {
                            putExtra("movieId", movieId)
                            startActivity(this)
                        }
                    }
                }
                is ResultState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Error -> {
                    Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
}