package com.example.sharesphere.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.local.HomePostDatabase
import com.example.sharesphere.data.mapper.toFFModelList
import com.example.sharesphere.data.mapper.toMyPostModelList
import com.example.sharesphere.data.mapper.toSavedPostModelList
import com.example.sharesphere.data.mapper.toSearchUserModelList
import com.example.sharesphere.data.mapper.toViewAccountModel
import com.example.sharesphere.data.paging.HomePostRemoteMediator
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.dto.user.home.like.LikePostDto
import com.example.sharesphere.data.remote.dto.user.home.save.SavePostDto
import com.example.sharesphere.data.remote.dto.user.post.CreatePostResDto
import com.example.sharesphere.domain.model.user.followersFollowing.FFModel
import com.example.sharesphere.domain.model.user.profile.MyPostModel
import com.example.sharesphere.domain.model.user.profile.SavedPostModel
import com.example.sharesphere.domain.model.user.profile.ViewAccountModel
import com.example.sharesphere.domain.model.user.search.SearchUserModel
import com.example.sharesphere.domain.repository.UserRepositoryInterface
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.Constants.ITEMS_PER_PAGE
import com.example.sharesphere.util.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImplementation @Inject constructor(
    private val shareSphereApi: ShareSphereApi,
    private val homePostDatabase: HomePostDatabase
) : UserRepositoryInterface {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllPosts(): Flow<PagingData<Post>> {
        val pagingSourceFactory = { homePostDatabase.postDao().getAllPosts() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HomePostRemoteMediator(
                shareSphereApi = shareSphereApi,
                homePostDatabase = homePostDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun likePost(postId: String): Flow<ApiResult<LikePostDto, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.likePost(postId)
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun savePost(postId: String): Flow<ApiResult<SavePostDto, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.savePost(postId)
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun viewAccount(userId: String): Flow<ApiResult<ViewAccountModel, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.viewAccount(userId).toViewAccountModel()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getMyPosts(): Flow<ApiResult<List<MyPostModel>, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.getMyPosts().toMyPostModelList()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getSavedPosts(): Flow<ApiResult<List<SavedPostModel>, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.getSavedPosts().toSavedPostModelList()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun createPost(
        postImages: List<MultipartBody.Part>,
        caption: RequestBody
    ): Flow<ApiResult<CreatePostResDto, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.createPost(postImages, caption)
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    413 -> emit(ApiResult.Error(DataError.Network.PAYLOAD_TOO_LARGE))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun searchUser(usernameOrName: String): Flow<ApiResult<List<SearchUserModel>, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.searchUser(usernameOrName).toSearchUserModelList()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun followUser(accountId: String): Flow<ApiResult<Unit, DataError.Network>> =
        flow {
            try {
                shareSphereApi.followUser(accountId)
                emit(ApiResult.Success(Unit))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getFollowers(userId: String): Flow<ApiResult<List<FFModel>, DataError.Network>>  =
        flow {
            try {
                val response=shareSphereApi.getFollowers(userId).toFFModelList()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getFollowing(userId: String): Flow<ApiResult<List<FFModel>, DataError.Network>>  =
        flow {
            try {
                val response=shareSphereApi.getFollowing(userId).toFFModelList()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }
}

