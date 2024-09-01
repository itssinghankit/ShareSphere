package com.example.sharesphere.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sharesphere.data.commonDto.user.home.post.Post

@Dao
interface HomePostDao {

    @Query("SELECT * FROM post_image_table")
    fun getAllPosts() : PagingSource<Int, Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPosts(posts: List<Post>)

    @Query("DELETE FROM post_image_table")
    suspend fun deleteAllPosts()

}