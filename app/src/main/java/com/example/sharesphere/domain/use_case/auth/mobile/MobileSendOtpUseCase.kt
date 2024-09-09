package com.example.sharesphere.domain.use_case.auth.mobile

import com.example.sharesphere.R
import com.example.sharesphere.data.remote.dto.auth.mobile.toMobileModel
import com.example.sharesphere.data.repository.AuthRepositoryImplementation
import com.example.sharesphere.domain.model.auth.MobileModel
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
            emit(ApiResponse.Error(UiText.StringResource(R.string.internetError)))
        }
    }.flowOn(Dispatchers.IO)

}
//TODO : added flow on for background thread calling