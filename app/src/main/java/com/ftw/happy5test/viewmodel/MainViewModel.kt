package com.ftw.happy5test.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftw.happy5test.model.Movies
import io.reactivex.disposables.CompositeDisposable

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val moviesData = MutableLiveData<MutableList<Movies>>()
    val error = MutableLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()


    fun getAllMovies() {
        compositeDisposable.add(
            repository.getAllMovies()
                .doOnSubscribe {
                    loading.postValue(true)
                }
                .subscribe({
                    loading.postValue(false)
                    val data = it.results as MutableList<Movies>
                    moviesData.postValue(data)
                    Log.d("MainVM", "getAllMovies: $data")
                }, {
                    error.postValue(it)
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