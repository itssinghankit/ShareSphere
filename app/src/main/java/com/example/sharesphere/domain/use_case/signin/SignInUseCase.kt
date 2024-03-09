package com.example.sharesphere.domain.use_case.signin

import com.example.sharesphere.R
import com.example.sharesphere.data.mapper.toSignInModel
import com.example.sharesphere.data.remote.dto.error.ServerErrorDto
import com.example.sharesphere.data.remote.dto.signin.SignInRequestDto
import com.example.sharesphere.domain.model.SignInModel
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.UiText
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepositoryInterface: AuthRepositoryInterface) {

    suspend operator fun invoke(
        usernameOrEmail: String,
        password: String
    ): Flow<ApiResponse<SignInModel>> = flow {
        try {
            emit(ApiResponse.Loading())
            val response = authRepositoryInterface.signIn(
                SignInRequestDto(
                    usernameOrEmail = usernameOrEmail,
                    password = password
                )
            )
            emit(ApiResponse.Success(response.toSignInModel()))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string() ?: ""
            val parsedError = Gson().fromJson(errorBody, ServerErrorDto::class.java)
            val errorMessage = parsedError?.errors?.message ?: "Server Error"
            emit(ApiResponse.Error(UiText.DynamicString(errorMessage)))

        } catch (e: Exception) {
            Timber.d("$e")
            emit(ApiResponse.Error(UiText.StringResource(R.string.internetError)))
        }

    }.flowOn(Dispatchers.IO)


}