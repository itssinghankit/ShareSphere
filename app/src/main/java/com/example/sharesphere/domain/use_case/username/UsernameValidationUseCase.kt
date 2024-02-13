package com.example.sharesphere.domain.use_case.username

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class UsernameValidationUseCase @Inject constructor() {
    operator fun invoke(username:String):Boolean{
        return TextFieldValidation.isUsernameValid(username)
    }
}