package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.remote.dto.UsernameResponseDto

interface AuthRepositoryInterface {

    suspend fun checkUsername(username:String):UsernameResponseDto


}