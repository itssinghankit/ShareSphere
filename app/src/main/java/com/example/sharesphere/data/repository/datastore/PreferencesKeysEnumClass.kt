package com.example.sharesphere.data.repository.datastore

enum class PreferencesKeys(val key: String) {
    Username("username"),
    Email("email"),
    Password("password"),
    Mobile("mobile"),
    Id("id"),
    Avatar("avatar"),
    FullName("fullName"),
    Bio("bio"),
    Dob("dob"),
    Gender("gender"),
    AccessToken("accessToken"),
    RefreshToken("refreshToken"),
    IsDetailsFilled("isDetailsFilled"),
    IsVerified("isVerified"),
    IsSignedIn("isSignedIn"),
}
