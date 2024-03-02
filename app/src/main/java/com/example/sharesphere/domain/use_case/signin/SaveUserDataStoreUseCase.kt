package com.example.sharesphere.domain.use_case.signin

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.model.SignInModel
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import javax.inject.Inject

class SaveUserDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {

    suspend operator fun invoke(details: SignInModel) {
        dataStoreRepositoryInterface.save(PreferencesKeys.Id, details._id)
        dataStoreRepositoryInterface.save(PreferencesKeys.Avatar, details.avatar)
        dataStoreRepositoryInterface.save(PreferencesKeys.Bio, details.bio)
        dataStoreRepositoryInterface.save(PreferencesKeys.Dob, details.dob)
        dataStoreRepositoryInterface.save(PreferencesKeys.Email, details.email)
        dataStoreRepositoryInterface.save(PreferencesKeys.FullName, details.fullName)
        dataStoreRepositoryInterface.save(PreferencesKeys.Gender, details.gender)
        dataStoreRepositoryInterface.save(PreferencesKeys.Mobile, details.mobile)
        dataStoreRepositoryInterface.save(PreferencesKeys.IsDetailsFilled, details.isDetailsFilled)
        dataStoreRepositoryInterface.save(PreferencesKeys.IsVerified, details.isVerified)
        dataStoreRepositoryInterface.save(PreferencesKeys.AccessToken, details.accessToken)
        dataStoreRepositoryInterface.save(PreferencesKeys.RefreshToken, details.refreshToken)
        dataStoreRepositoryInterface.save(PreferencesKeys.IsSignedIn, true)

    }

}