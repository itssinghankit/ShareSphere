package com.example.sharesphere.domain.use_case.auth.mobile

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveMobileDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(mobile: String) {
        dataStoreRepository.save(PreferencesKeys.MOBILE, mobile)
    }
}