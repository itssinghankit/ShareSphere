package com.example.sharesphere.domain.use_case.avatar

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.AvatarModel
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import javax.inject.Inject

class SaveAvatarDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface){
    suspend operator fun invoke(data: AvatarModel){
        dataStoreRepositoryInterface.save(PreferencesKeys.Gender,data.gender)
        dataStoreRepositoryInterface.save(PreferencesKeys.Dob,data.dob)
        dataStoreRepositoryInterface.save(PreferencesKeys.FullName,data.fullName)
        dataStoreRepositoryInterface.save(PreferencesKeys.Bio,data.bio)
        dataStoreRepositoryInterface.save(PreferencesKeys.Avatar,data.avatar)
        dataStoreRepositoryInterface.save(PreferencesKeys.IsDetailsFilled,data.isDetailsFilled)
        dataStoreRepositoryInterface.save(PreferencesKeys.IsAuthenticated,true)
    }
}