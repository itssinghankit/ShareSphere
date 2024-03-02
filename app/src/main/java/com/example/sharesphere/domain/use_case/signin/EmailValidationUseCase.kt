package com.example.sharesphere.domain.use_case.signin

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class EmailValidationUseCase @Inject constructor(){
    operator fun invoke(email:String):Boolean{
        return TextFieldValidation.isEmailValid(email)
    }
}