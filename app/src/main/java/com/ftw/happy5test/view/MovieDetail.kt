package com.ftw.happy5test.view

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ftw.happy5test.databinding.ActivityMovieDetailBinding
import com.ftw.happy5test.model.ResponseMovieDetail
import com.ftw.happy5test.utils.BASE_URL_IMG
import com.ftw.happy5test.utils.BASE_URL_IMG_BACKDROP
import com.ftw.happy5test.utils.ResultState
import com.ftw.happy5test.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    lateinit var movieDetailViewModel: MovieDetailViewModel
    private var movieId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val TAG = MovieDetail::class.java.simpleName
        movieId = intent.getIntExtra("movieId", 0)
        movieDetailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.getMovieDetail(movieId)
        movieDetailViewModel.mutableResultState.observe(this, Observer { state ->
            when (state) {
                is ResultState.Success<*> -> {
                    val dataDetail: ResponseMovieDetail = state.data as ResponseMovieDetail
                    binding.movieDetail = dataDetail
                    Log.d(TAG, "onCreateMovieDetail: $dataDetail")
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

    companion object {
        @JvmStatic
        @BindingAdapter("loadPosterImg")
        fun loadPosterImg(posterImg: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(posterImg)
                    .load(BASE_URL_IMG + url)
                    .transform(RoundedCorners(8))
                    .into(posterImg)
            }
        }

        @JvmStatic
        @BindingAdapter("loadBackdropImg")
        fun loadBackdropImg(backdropImg: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(backdropImg)
                    .load(BASE_URL_IMG_BACKDROP + url)
                    .into(backdropImg)
            }
        }
    }
}