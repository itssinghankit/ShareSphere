package com.example.sharesphere.domain.use_case.verifyotp

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.SignInModel
import com.example.sharesphere.domain.model.VerifyOtpModel
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import javax.inject.Inject

class SaveVerifyOtpDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {
    suspend operator fun invoke(details: VerifyOtpModel) {
        dataStoreRepositoryInterface.save(PreferencesKeys.IsVerified, details.isVerified)
        dataStoreRepositoryInterface.save(PreferencesKeys.Mobile, details.mobile)
    }
}