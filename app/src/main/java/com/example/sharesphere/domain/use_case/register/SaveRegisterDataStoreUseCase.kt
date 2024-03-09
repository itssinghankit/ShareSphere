package com.example.sharesphere.domain.use_case.register

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.RegisterModel
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import javax.inject.Inject

class SaveRegisterDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {
    suspend operator fun invoke(data:RegisterModel){
        dataStoreRepositoryInterface.save(PreferencesKeys.Email,data.email)
        dataStoreRepositoryInterface.save(PreferencesKeys.Username,data.username)
        dataStoreRepositoryInterface.save(PreferencesKeys.AccessToken,data.accessToken)
        dataStoreRepositoryInterface.save(PreferencesKeys.RefreshToken,data.refreshToken)
    }
}