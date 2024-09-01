package com.example.sharesphere.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sharesphere.data.commonDto.user.home.post.PostRemoteKeysDto

@Dao
interface HomePostRemoteKeysDao {

    @Query("SELECT * FROM post_remote_keys_table WHERE id = :postId")
    suspend fun getRemoteKeys(postId: String): PostRemoteKeysDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PostRemoteKeysDto>)

    @Query("DELETE FROM post_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}