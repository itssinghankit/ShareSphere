package com.example.sharesphere.domain.newstructure

typealias RootError = Error
sealed interface ApiResult2<out D,out E:RootError> {
//    data class Success<D>(val data: D) : ApiResult2<D, Nothing>
//    data class Failure<E:RootError>(val error: E) : ApiResult2<Nothing, E>
    data class Success<out D,out E:RootError>(val data:D):ApiResult2<D,E>
    data class Error<out D,out E:RootError>(val error: E):ApiResult2<D,E>

}
