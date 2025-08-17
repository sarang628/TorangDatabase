package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.data.entity.LikeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {

    @Insert
    suspend fun insertLike(resultLike: LikeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikes(users: List<LikeEntity>)

    @Delete
    suspend fun deleteLike(like: LikeEntity)

    @Delete
    suspend fun deleteLikes(users: List<LikeEntity>)

    @Query("delete from LikeEntity")
    suspend fun deleteAll()

    @Query("select * from LikeEntity where reviewId = (:reviewId)")
    fun getLike(reviewId: Int): Flow<LikeEntity>

    @Query("select * from LikeEntity where reviewId = (:reviewId)")
    suspend fun getLike1(reviewId: Int): LikeEntity

    @Query("select count(*) from LikeEntity where reviewId = (:reviewId)")
    suspend fun hasLike(reviewId: Int): Int
}