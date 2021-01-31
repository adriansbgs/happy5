package com.ftw.happy5test.utils

sealed class ResultState {
    object loading : ResultState()
    data class Success<T>(val data: T, val msg: String) : ResultState()
    data class Error(val throwable: Throwable) : ResultState()
}