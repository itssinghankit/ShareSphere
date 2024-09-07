package com.example.sharesphere.domain.model.user.profile

import com.example.sharesphere.data.remote.dto.user.profile.PostDetails
import com.example.sharesphere.data.remote.dto.user.profile.PostedBy

data class SavedPostModel(
    val postDetails: PostDetails,
    val postedBy: PostedBy
)
