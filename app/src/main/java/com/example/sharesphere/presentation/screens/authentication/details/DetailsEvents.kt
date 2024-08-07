package com.example.sharesphere.presentation.screens.authentication.details

import com.example.sharesphere.presentation.screens.authentication.mobile.MobileEvents

sealed class DetailsEvents {
    data class OnFullNameValueChange(val fullName: String) : DetailsEvents()
    data class OnGenderSelected(val gender: Gender) : DetailsEvents()

    //    data class OnDateChanged(val date:Long):DetailsEvents()
    data class OnNextClicked(val date: Long?) : DetailsEvents()
    object OnSnackBarShown : DetailsEvents()
    object OnNavigationDone : DetailsEvents()


}