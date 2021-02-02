package com.ftw.happy5test.utils

sealed class ResultState {
    class Loading : ResultState()
    class Success<T>(val data: T) : ResultState()
    class Error : ResultState()
}