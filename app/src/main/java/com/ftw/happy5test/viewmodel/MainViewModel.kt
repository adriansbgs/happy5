package com.ftw.happy5test.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftw.happy5test.utils.ResultState
import io.reactivex.disposables.CompositeDisposable

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val mutableResultState = MutableLiveData<ResultState>()


    fun getAllMovies() {
        compositeDisposable.add(
            repository.getAllMovies()
                .doOnSubscribe {
                    mutableResultState.value = ResultState.Loading()
                }
                .subscribe({
                    mutableResultState.value = ResultState.Loading()

                    mutableResultState.value = ResultState.Success(it.results)
                }, {
                    mutableResultState.value = ResultState.Error()
                    Log.d("MainVM", "getAllMovies ERROR: $it")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}