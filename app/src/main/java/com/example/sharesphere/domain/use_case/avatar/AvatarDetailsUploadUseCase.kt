package com.example.sharesphere.domain.use_case.avatar

import android.net.Uri
import com.example.sharesphere.R
import com.example.sharesphere.data.mapper.toAvatarModel
import com.example.sharesphere.data.remote.dto.error.ServerErrorDto
import com.example.sharesphere.domain.model.AvatarModel
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.UiText
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class AvatarDetailsUploadUseCase @Inject constructor(
    private val authRepositoryInterface: AuthRepositoryInterface,
    @ApplicationContext val context: android.content.Context
) {
    suspend operator fun invoke(
        avatarUri: Uri,
        fullName: String,
        dob: Long,
        gender: String,
        bio: String
    ): Flow<ApiResponse<AvatarModel>> = flow {
        try {

            //now we have to make our file from the above uri as we directly cant access it due to security concerns as an application is only allowed to access its own content
            val filDir = context.filesDir
            val file = File(filDir, "avatar.jpg")
            val inputStream = context.contentResolver.openInputStream(avatarUri)
            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)

            val avatarPart = MultipartBody.Part.createFormData(
                "avatar",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )

            val fullNamePart = fullName.toRequestBody("text/plain".toMediaTypeOrNull())
            val genderPart = gender.toRequestBody("text/plain".toMediaTypeOrNull())
            val bioPart = bio.toRequestBody("text/plain".toMediaTypeOrNull())
            val dobPart = dob.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            emit(ApiResponse.Loading())
            val response =
                authRepositoryInterface.details(
                    avatarPart, genderPart, bioPart, dobPart, fullNamePart
                )
                    .toAvatarModel()
            emit(ApiResponse.Success(response))


        } catch (e: HttpException) {
            e.printStackTrace()
            val error = e.response()?.errorBody()?.string() ?: ""
            val parsedError = Gson().fromJson(error, ServerErrorDto::class.java)
            val errorMessage = parsedError?.errors?.message ?: ""
            emit(ApiResponse.Error(UiText.DynamicString(errorMessage)))

        } catch (e: IOException) {
            e.printStackTrace()
            emit(ApiResponse.Error(UiText.StringResource(R.string.internetError)))
        }

    }.flowOn(Dispatchers.IO)
}