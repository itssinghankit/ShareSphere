package com.example.sharesphere.domain.use_case.auth.verifyotp

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.auth.VerifyOtpModel
import com.example.sharesphere.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveVerifyOtpDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(details: VerifyOtpModel) {
        dataStoreRepository.save(PreferencesKeys.IS_VERIFIED, details.isVerified)
        dataStoreRepository.save(PreferencesKeys.MOBILE, details.mobile)
    }
}