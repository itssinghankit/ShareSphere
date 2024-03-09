package com.example.sharesphere.domain.use_case.username

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.sharesphere.data.remote.dto.error.ServerErrorDto
import com.example.sharesphere.data.remote.dto.username.toUsernameModel
import com.example.sharesphere.domain.model.UsernameModel
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.UiText
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CheckAvailabilityUseCase @Inject constructor(private val authRepositoryInterface: AuthRepositoryInterface) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(username: String): Flow<ApiResponse<UsernameModel>> = flow {
        try {

            emit(ApiResponse.Loading())
            val response = authRepositoryInterface.checkUsername(username).toUsernameModel()
            emit(ApiResponse.Success(response))

        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string() ?: ""
            val parsedError = Gson().fromJson(errorBody, ServerErrorDto::class.java)
            emit(ApiResponse.Error(UiText.DynamicString(parsedError.errors.message ?: "Server Error")))

        } catch (e: IOException) {
            //also calls when server is not responding
            emit(ApiResponse.Error(UiText.DynamicString("Check your Internet")))

        }
    }

}
