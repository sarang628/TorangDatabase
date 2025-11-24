package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Query

import com.sarang.torang.core.database.model.image.ReviewImageEntity;
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewImageDao {
    @Query("""
        SELECT * 
        FROM ReviewImageEntity 
        WHERE reviewId = (:reviewId)""")           fun getReviewImages(reviewId: Int): Flow<List<ReviewImageEntity>>
    @Query(""" DELETE 
        FROM ReviewImageEntity 
        WHERE reviewId = (:reviewId)
        """)    suspend  fun deleteByReviewId(reviewId: Int)
}