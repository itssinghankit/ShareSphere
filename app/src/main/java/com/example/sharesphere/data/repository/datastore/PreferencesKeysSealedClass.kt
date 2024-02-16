package com.example.sharesphere.data.repository.datastore

sealed class PreferencesKeys(val key: String) {
    object Username : PreferencesKeys("username")
    object Email : PreferencesKeys("email")
    object Password : PreferencesKeys("password")
    object AccessToken : PreferencesKeys("accessToken")
    object Mobile : PreferencesKeys("mobile")
}