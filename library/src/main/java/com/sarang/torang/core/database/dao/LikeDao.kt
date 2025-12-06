package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.like.LikeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {
    @Query("""SELECT * 
                      FROM LikeEntity 
                      WHERE reviewId = (:reviewId)""")         fun findByReviewIdFlow(reviewId: Int)        : Flow<LikeEntity>
    @Query("""SELECT * 
                      FROM LikeEntity 
                      WHERE reviewId = (:reviewId)""") suspend fun findByReviewId(reviewId: Int)            : LikeEntity?
    @Insert                    suspend fun add(like: LikeEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
                               suspend fun addAll(likes: List<LikeEntity>)
    @Query("""DELETE
                      FROM LikeEntity
                      WHERE likeId = :likeId""")   suspend fun delete(likeId: Int)
    @Query("""DELETE 
                      FROM LikeEntity""")   suspend fun deleteAll()
    @Delete                    suspend fun deleteAll(likes: List<LikeEntity>)
    @Query("""SELECT 
                             count(*) 
                      FROM LikeEntity 
                      WHERE reviewId = (:reviewId)""")   suspend fun has(reviewId: Int)                      : Boolean
}