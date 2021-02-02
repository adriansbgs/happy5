@file:Suppress("PropertyName")

package com.ftw.happy5test.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftw.happy5test.utils.ResultState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel @ViewModelInject constructor(private val repository: MovieDetailRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val mutableResultState = MutableLiveData<ResultState>()
    private val TAG = MovieDetailViewModel::class.java.simpleName

    fun getMovieDetail(movieId: Int) {
        compositeDisposable.add(
            repository.getMovieDetail(movieId).doOnSubscribe {
                mutableResultState.value = ResultState.Loading()
            }.subscribe({
                mutableResultState.value = ResultState.Loading()
                mutableResultState.value = ResultState.Success(it)
            }, {
                mutableResultState.value = ResultState.Error()
                Log.d(TAG, "getMovieDetail: ERROR $it")
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}