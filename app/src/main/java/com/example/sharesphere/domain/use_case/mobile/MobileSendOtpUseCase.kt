package com.example.sharesphere.domain.use_case.mobile

import com.example.sharesphere.R
import com.example.sharesphere.data.remote.dto.ServerErrorDto
import com.example.sharesphere.data.remote.dto.toMobileModel
import com.example.sharesphere.data.repository.AuthRepositoryImplementation
import com.example.sharesphere.domain.model.MobileModel
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.UiText
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MobileSendOtpUseCase @Inject constructor(private val authRepositoryImplementation: AuthRepositoryImplementation) {

    suspend operator fun invoke(mobile:String): Flow<ApiResponse<MobileModel>> = flow {
        try {
            emit(ApiResponse.Loading())
            val response = authRepositoryImplementation.mobileSendOtp(mobile).toMobileModel()
            emit(ApiResponse.Success(response))

        }catch (e:HttpException){

//            val errorBody = e.response()?.errorBody()?.string() ?: ""
//            val parsedError = Gson().fromJson(errorBody, ServerErrorDto::class.java)
//            Timber.d("$parsedError")
//            emit(ApiResponse.Error(UiText.DynamicString(parsedError.errors.message ?: "Server Error")))
            emit(ApiResponse.Error(UiText.StringResource(R.string.mobileOtpError)))

        }catch(e:IOException){
            Timber.d("$e")
            emit(ApiResponse.Error(UiText.DynamicString("Check your Internet")))
        }
    }

}