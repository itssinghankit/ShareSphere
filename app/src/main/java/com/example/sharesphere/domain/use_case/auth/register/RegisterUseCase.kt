package com.example.sharesphere.domain.use_case.auth.register

import com.example.sharesphere.R
import com.example.sharesphere.data.mapper.toRegisterModel
import com.example.sharesphere.data.remote.dto.auth.error.ServerErrorDto
import com.example.sharesphere.data.remote.dto.auth.register.RegisterRequestDto
import com.example.sharesphere.domain.model.auth.RegisterModel
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.UiText
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepositoryInterface: AuthRepositoryInterface) {

    suspend operator fun invoke(
        email: String,
        password: String,
        username: String
    ): Flow<ApiResponse<RegisterModel>> = flow {
        try {
            emit(ApiResponse.Loading())
            val response =
                authRepositoryInterface.register(RegisterRequestDto(email, password, username))
            emit(ApiResponse.Success(response.toRegisterModel()))

        } catch (e: retrofit2.HttpException) {

            val error = e.response()?.errorBody()?.string() ?: ""
            val parsedError = Gson().fromJson(error, ServerErrorDto::class.java)
            val errorMessage = parsedError?.errors?.message ?: ""
            emit(ApiResponse.Error(UiText.DynamicString(errorMessage)))

        } catch (e: IOException) {
            emit(ApiResponse.Error(UiText.StringResource(R.string.internetError)))
        }
    }.flowOn(Dispatchers.IO)

}