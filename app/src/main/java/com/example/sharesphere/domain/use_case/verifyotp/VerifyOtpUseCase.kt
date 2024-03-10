package com.example.sharesphere.domain.use_case.verifyotp

import com.example.sharesphere.R
import com.example.sharesphere.data.mapper.toVerifyOtpModel
import com.example.sharesphere.data.remote.dto.error.ServerErrorDto
import com.example.sharesphere.data.remote.dto.verifyotp.VerifyOtpRequestDto
import com.example.sharesphere.domain.model.VerifyOtpModel
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

class VerifyOtpUseCase @Inject constructor(private val authRepositoryInterface: AuthRepositoryInterface) {
    suspend operator fun invoke(
        emailOtp: String,
        mobileOtp: String
    ): Flow<ApiResponse<VerifyOtpModel>> = flow {

        try {

            emit(ApiResponse.Loading())
            val response =
                authRepositoryInterface.verifyOtp(VerifyOtpRequestDto(emailOtp, mobileOtp))
                    .toVerifyOtpModel()
            emit(ApiResponse.Success(response))

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