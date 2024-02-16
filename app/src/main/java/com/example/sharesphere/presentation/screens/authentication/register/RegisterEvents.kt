package com.example.sharesphere.presentation.screens.authentication.register

sealed class RegisterEvents{
    data class EmailOnValueChange(val email:String):RegisterEvents()
    data class PasswordOnValueChange(val password:String):RegisterEvents()
    data class CPasswordOnValueChange(val cPassword:String):RegisterEvents()
    object onNextClick:RegisterEvents()
}
