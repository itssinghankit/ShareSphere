package com.example.sharesphere.domain.use_case.username

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.sharesphere.common.ApiResponse
import com.example.sharesphere.data.remote.dto.toUsernameModel
import com.example.sharesphere.domain.model.UsernameModel
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
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
        } catch (e: HttpException) {
            emit(ApiResponse.Error(e.localizedMessage?:"Error from backend"))

        } catch (e: IOException) {
            emit(ApiResponse.Error("Check your Internet"))

        }
    }

}
