package com.example.sharesphere.domain.use_case.auth.register

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class CPasswordValidationUseCase @Inject constructor(){
    operator fun invoke(password:String,cPassword: String): Boolean {
        return TextFieldValidation.isBothPasswordSame(password,cPassword)
    }
}