package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.profile.SavedPostsDto
import com.example.sharesphere.domain.model.user.profile.SavedPostModel

fun SavedPostsDto.toSavedPostModelList():List<SavedPostModel>{
    return this.data.map {
        SavedPostModel(
            postDetails = it.postDetails,
            postedBy = it.postedBy
        )
    }
}