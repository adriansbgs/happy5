package com.ftw.happy5test.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ftw.happy5test.databinding.ActivityMainBinding
import com.ftw.happy5test.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.button.setOnClickListener {
            movieViewModel.getAllMovies()
            movieViewModel.moviesData.observe(this, Observer {
                Log.d("MainActivity", "onCreate: $it")
            })
        }
    }
}