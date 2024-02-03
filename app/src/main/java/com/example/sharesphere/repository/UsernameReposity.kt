package com.example.sharesphere.repository

import android.util.Log
import com.example.sharesphere.common.ApiResponse
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.dto.UsernameResponseDto
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject
import javax.inject.Inject

class UsernameRepository @Inject constructor(private val retrofitInterface: ShareSphereApi) {
    //backing property
    private val _usernameResponseFlow =
        MutableStateFlow<ApiResponse<UsernameResponseDto>>(ApiResponse.Initial())
    val usernameResponseFlow: MutableStateFlow<ApiResponse<UsernameResponseDto>>
        get() = _usernameResponseFlow

//    suspend fun username(username: String) {
//        _usernameResponseFlow.emit(ApiResponse.Loading())
//        try {
//            val response = retrofitInterface.checkUsername(username)
//            if (response.isSuccessful && response.body() != null) {
//                _usernameResponseFlow.emit(ApiResponse.Success(response.body()!!))
//                Log.d("meow", "hello ${response.body()}")
//                Log.d("meow", response.code().toString())
//            } else if (response.errorBody() != null) {
//                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _usernameResponseFlow.emit(ApiResponse.Error(errObj.getString("message")))
//            } else {
//                _usernameResponseFlow.emit(ApiResponse.Error("Something went wrong"))
//            }
//        } catch (e: Exception) {
//            _usernameResponseFlow.emit(ApiResponse.Error("Check your Internet Connection"))
//        }
//    }

}