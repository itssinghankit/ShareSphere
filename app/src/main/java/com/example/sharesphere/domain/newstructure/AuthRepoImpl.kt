package com.example.sharesphere.domain.newstructure

import okio.IOException
import retrofit2.HttpException

class AuthRepoImpl():AuthRepo {
    override suspend fun login(password: String): ApiResult2<User,DataError.Network> {
        try {
            val response= User("Ankit","ankit.com","password")
            return ApiResult2.Success(response)
        }catch (e:HttpException){
            when(e.code()){
                404-> return ApiResult2.Error(DataError.Network.NOT_FOUND)
                500-> return ApiResult2.Error(DataError.Network.INTERNAL_SERVER_ERROR)
                else-> return ApiResult2.Error(DataError.Network.UNKNOWN)
            }
        }catch (e:IOException){
            return ApiResult2.Error(DataError.Network.NO_INTERNET)
        }
    }
}