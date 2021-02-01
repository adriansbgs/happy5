package com.ftw.happy5test.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftw.happy5test.model.Movies
import com.ftw.happy5test.utils.ResultState
import io.reactivex.disposables.CompositeDisposable

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val mutableResultState = MutableLiveData<ResultState>()


    fun getAllMovies() {
        compositeDisposable.add(
            repository.getAllMovies()
                .doOnSubscribe {
                    mutableResultState.value = ResultState.Loading(true)
                }
                .subscribe({
                    mutableResultState.value = ResultState.Loading(false)

                    mutableResultState.value = ResultState.Success(it.results)
                }, {
                    mutableResultState.value = ResultState.Error(it)
                    Log.d("MainVM", "getAllMovies ERROR: $it")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /*class Factory@Inject
    constructor(private val repository: MainRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            try {
                return MainViewModel(repository) as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }*/
}