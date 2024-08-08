package com.example.sharesphere.domain.use_case.splash

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.splash.AuthModel
import com.example.sharesphere.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAuthDetailsDataStore @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): AuthModel {
        return AuthModel(
            isSignedUp = dataStoreRepository.getBoolean(PreferencesKeys.IS_SIGNED_UP).first()?:false,
            isVerified = dataStoreRepository.getBoolean(PreferencesKeys.IS_VERIFIED).first()?:false,
            isDetailsFilled = dataStoreRepository.getBoolean(PreferencesKeys.IS_DETAILS_FILLED).first()?:false
        )
    }

}