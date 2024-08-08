package com.example.sharesphere.domain.use_case.auth.avatar

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.auth.AvatarModel
import com.example.sharesphere.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveAvatarDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository){
    suspend operator fun invoke(data: AvatarModel){
        dataStoreRepository.save(PreferencesKeys.GENDER,data.gender)
        dataStoreRepository.save(PreferencesKeys.DOB,data.dob)
        dataStoreRepository.save(PreferencesKeys.FULL_NAME,data.fullName)
        dataStoreRepository.save(PreferencesKeys.BIO,data.bio)
        dataStoreRepository.save(PreferencesKeys.AVATAR,data.avatar)
        dataStoreRepository.save(PreferencesKeys.IS_DETAILS_FILLED,data.isDetailsFilled)
    }
}