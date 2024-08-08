package com.example.sharesphere.domain.use_case.auth.register

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.auth.RegisterModel
import com.example.sharesphere.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveRegisterDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(data: RegisterModel){
        dataStoreRepository.save(PreferencesKeys.EMAIL,data.email)
        dataStoreRepository.save(PreferencesKeys.USERNAME,data.username)
        dataStoreRepository.save(PreferencesKeys.ACCESS_TOKEN,data.accessToken)
        dataStoreRepository.save(PreferencesKeys.REFRESH_TOKEN,data.refreshToken)
        dataStoreRepository.save(PreferencesKeys.IS_SIGNED_UP,true)
    }
}