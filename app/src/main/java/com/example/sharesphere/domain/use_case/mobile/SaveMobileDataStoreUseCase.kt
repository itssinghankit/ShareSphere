package com.example.sharesphere.domain.use_case.mobile

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import javax.inject.Inject

class SaveMobileDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {
    suspend operator fun invoke(mobile: String) {
        dataStoreRepositoryInterface.save(PreferencesKeys.Mobile, mobile)
    }
}