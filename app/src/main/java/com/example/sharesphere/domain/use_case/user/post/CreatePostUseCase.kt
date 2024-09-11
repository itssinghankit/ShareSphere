package com.example.sharesphere.domain.use_case.user.post

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import com.example.sharesphere.data.remote.dto.user.post.CreatePostResDto
import com.example.sharesphere.domain.repository.UserRepositoryInterface
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface,
    @ApplicationContext val context: Context
) {
    suspend operator fun invoke(
        caption: String,
        postImages: List<Uri>
    ): Flow<ApiResult<CreatePostResDto, DataError.Network>> {
        val imageListPart = postImages.mapNotNull { uri ->
            try {
                val file = context.compressAndRotateImage(uri)
                MultipartBody.Part.createFormData(
                    "postImages",
                    file.name,
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            } catch (e: Exception) {
                // Log the error and continue with the next image
                null
            }
        }
        val captionPart = caption.toRequestBody("text/plain".toMediaTypeOrNull())
        return userRepositoryInterface.createPost(imageListPart, captionPart)
    }

    private suspend fun Context.compressAndRotateImage(
        imageUri: Uri,
        maxFileSizeKB: Int = 1024
    ): File = withContext(Dispatchers.IO) {
        contentResolver.openInputStream(imageUri)?.use { inputStream ->
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            val orientation = when (imageUri.scheme) {
                "content" -> contentResolver.query(
                    imageUri,
                    arrayOf(MediaStore.Images.Media.ORIENTATION),
                    null,
                    null,
                    null
                )?.use { cursor ->
                    if (cursor.moveToFirst()) cursor.getInt(0) else 0
                } ?: 0

                "file" -> ExifInterface(imageUri.path!!).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                else -> ExifInterface.ORIENTATION_NORMAL
            }

            val matrix = Matrix().apply {
                when (orientation) {
                    90 -> postRotate(90f)
                    180 -> postRotate(180f)
                    270 -> postRotate(270f)
                    ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270f)
                    ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> postScale(-1f, 1f)
                    ExifInterface.ORIENTATION_FLIP_VERTICAL -> postScale(1f, -1f)
                }
            }

            val rotatedBitmap = Bitmap.createBitmap(
                originalBitmap,
                0,
                0,
                originalBitmap.width,
                originalBitmap.height,
                matrix,
                true
            )

            ByteArrayOutputStream().use { bmpStream ->
                var compressQuality = 100
                do {
                    bmpStream.reset()
                    rotatedBitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        compressQuality,
                        bmpStream
                    )
                    compressQuality -= 5
                } while (bmpStream.size() > maxFileSizeKB * 1024 && compressQuality > 5)

                File(cacheDir, "compressed_${System.currentTimeMillis()}.jpg").apply {
                    outputStream().use { fileOut ->
                        bmpStream.writeTo(fileOut)
                    }
                }
            }
        } ?: throw IllegalArgumentException("Failed to open input stream for URI: $imageUri")
    }

}
