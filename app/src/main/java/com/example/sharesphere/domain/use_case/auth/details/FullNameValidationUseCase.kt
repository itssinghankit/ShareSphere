package com.example.sharesphere.domain.use_case.auth.details

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class FullNameValidationUseCase @Inject constructor() {
    operator fun invoke(fullName:String):Boolean{
        return TextFieldValidation.isFullNameValid(fullName)
    }
}