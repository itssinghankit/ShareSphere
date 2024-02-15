package com.example.sharesphere.util

sealed class ApiResponse<T>(val data: T? = null, val message: UiText? = null) {
    class Success<T>(data: T) : ApiResponse<T>(data)
    class Error<T>(message: UiText?, data: T? = null) : ApiResponse<T>(data, message)
    class Loading<T>() : ApiResponse<T>(null)

}

//isloading true false parameter in loading