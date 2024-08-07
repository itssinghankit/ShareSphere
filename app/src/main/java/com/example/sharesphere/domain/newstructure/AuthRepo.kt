package com.example.sharesphere.domain.newstructure

interface AuthRepo {
    suspend fun login(password:String):ApiResult2<User,DataError>

}
data class User(val name:String,val email:String,val password:String)