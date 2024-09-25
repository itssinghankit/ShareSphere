package com.example.sharesphere.data.remote

import com.example.sharesphere.data.remote.dto.chat.chat.GetChatsResDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface ShareSphereChatApi {

    @Headers("AddAuthorizationHeader: true")
    @GET("chat/all")
    suspend fun getChats(): GetChatsResDto

}