package com.example.sharesphere.data.repository.datastore

enum class PreferencesKeys(val key: String) {
    USERNAME("username"),
    EMAIL("email"),
    MOBILE("mobile"),
    ID("id"),
    AVATAR("avatar"),
    FULL_NAME("fullName"),
    BIO("bio"),
    DOB("dob"),
    GENDER("gender"),
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),
    IS_DETAILS_FILLED("isDetailsFilled"),
    IS_VERIFIED("isVerified"),
    IS_SIGNED_UP("isSignedIn")
}
