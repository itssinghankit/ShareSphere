package com.example.sharesphere.domain.use_case.user.post

import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class ValidateCaptionUseCase @Inject constructor() {

    operator fun invoke(caption: String): ApiResult<Unit, DataError> {
        return if (caption.isEmpty() || caption.isBlank()) {
            ApiResult.Error(DataError.Local.EMPTY)
        } else if (!TextFieldValidation.isCaptionValid(caption)) {
            ApiResult.Error(DataError.Local.INCORRECT_LENGTH)
        } else {
            ApiResult.Success(Unit)
        }
    }
}