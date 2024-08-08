package com.example.sharesphere.domain.use_case.auth.signin

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.auth.SignInModel
import com.example.sharesphere.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveUserDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(details: SignInModel) {
        dataStoreRepository.save(PreferencesKeys.ID, details._id)
        dataStoreRepository.save(PreferencesKeys.AVATAR, details.avatar)
        dataStoreRepository.save(PreferencesKeys.BIO, details.bio)
        dataStoreRepository.save(PreferencesKeys.DOB, details.dob)
        dataStoreRepository.save(PreferencesKeys.USERNAME,details.username)
        dataStoreRepository.save(PreferencesKeys.EMAIL, details.email)
        dataStoreRepository.save(PreferencesKeys.FULL_NAME, details.fullName)
        dataStoreRepository.save(PreferencesKeys.GENDER, details.gender)
        dataStoreRepository.save(PreferencesKeys.MOBILE, details.mobile)
        dataStoreRepository.save(PreferencesKeys.IS_DETAILS_FILLED, details.isDetailsFilled)
        dataStoreRepository.save(PreferencesKeys.IS_VERIFIED, details.isVerified)
        dataStoreRepository.save(PreferencesKeys.ACCESS_TOKEN, details.accessToken)
        dataStoreRepository.save(PreferencesKeys.REFRESH_TOKEN, details.refreshToken)
        dataStoreRepository.save(PreferencesKeys.IS_SIGNED_UP, true)

    }

}