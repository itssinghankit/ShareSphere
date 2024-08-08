package com.example.sharesphere.domain.use_case.auth.register

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class PasswordValidationUseCase @Inject constructor(){

    operator fun invoke(password: String): Boolean {
        return TextFieldValidation.isPasswordValid(password)
    }

}