package com.example.sharesphere.domain.use_case.avatar

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class BioValidationUseCase @Inject constructor() {
    operator fun invoke(bio: String): Boolean {
        return TextFieldValidation.isBioValid(bio)
    }
}