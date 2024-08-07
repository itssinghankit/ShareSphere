package com.example.sharesphere.domain.newstructure

sealed interface DataError:Error {
    enum class Network:DataError{
        NO_INTERNET,
        INTERNAL_SERVER_ERROR,
        NOT_FOUND,
        UNAUTHORIZED,
        TIMEOUT,
        UNKNOWN
    }
    enum class Local:DataError{
        DISK_FULL,
        STORAGE_PERMISSION_DENIED
    }
}