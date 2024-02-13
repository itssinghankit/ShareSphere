package com.example.sharesphere.domain.use_case.username

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.sharesphere.R
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.data.remote.dto.toUsernameModel
import com.example.sharesphere.domain.model.UsernameModel
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import com.example.sharesphere.util.TextFieldValidation
import com.example.sharesphere.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class CheckAvailabilityUseCase @Inject constructor(private val authRepositoryInterface: AuthRepositoryInterface) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(username: String): Flow<ApiResponse<UsernameModel>> = flow {
        try {
            emit(ApiResponse.Loading(isLoading = true))
            val response = authRepositoryInterface.checkUsername(username).toUsernameModel()
            emit(ApiResponse.Success(response))
            //check if username is valid
//            if(!TextFieldValidation.isUsernameValid(username)){
//                emit(ApiResponse.Error(UiText.StringResource(R.string.validateUsernameError)))
//            }else{
//
//            }
        } catch (e: HttpException) {
            emit(ApiResponse.Error(UiText.DynamicString(e.localizedMessage?:"Server Error")))

        } catch (e: IOException) {
            //also calls when server is not responding
            emit(ApiResponse.Error(UiText.DynamicString("Check your Internet")))

        }
    }

}
