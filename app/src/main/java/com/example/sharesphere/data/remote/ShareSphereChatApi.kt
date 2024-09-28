package com.example.sharesphere.data.remote

import com.example.sharesphere.data.remote.dto.chat.chat.GetChatsResDto
import com.example.sharesphere.data.remote.dto.chat.chat.oneone.CreateOrGetOneOneChatResDto
import com.example.sharesphere.data.remote.dto.chat.chatMessage.getAllMsgs.GetAllMsgsDto
import com.example.sharesphere.data.remote.dto.chat.chatMessage.sendMsg.SendMsgReqDto
import com.example.sharesphere.data.remote.dto.chat.chatMessage.sendMsg.SendMsgResDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ShareSphereChatApi {

    @Headers("AddAuthorizationHeader: true")
    @GET("chat/all")
    suspend fun getChats(): GetChatsResDto

    @Headers("AddAuthorizationHeader: true")
    @POST("chat/oneone/{receiverId}")
    suspend fun createOrGetOneOneChat(@Path("receiverId") receiverId:String): CreateOrGetOneOneChatResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("message/get-all-messages/{chatId}")
    suspend fun getAllMsgs(@Path("chatId") chatId: String): GetAllMsgsDto

    @Headers("AddAuthorizationHeader: true")
    @POST("message/send/{chatId}")
    suspend fun sendMessage(
        @Path("chatId") chatId: String,
        @Body sendMsgReqDto: SendMsgReqDto
    ): SendMsgResDto



}