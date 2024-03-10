package com.example.sharesphere.presentation.screens.authentication.verifyOtp

sealed class VerifyOtpEvents() {
    object onNextClicked:VerifyOtpEvents()
    data class onMobileOtpValueChange(val mobileOtp:String):VerifyOtpEvents()
    data class onEmailOtpValueChange(val emailOtp:String):VerifyOtpEvents()
    object onSnackBarShown:VerifyOtpEvents()
    object onResendClicked:VerifyOtpEvents()
    object onNavigationDone:VerifyOtpEvents()

}