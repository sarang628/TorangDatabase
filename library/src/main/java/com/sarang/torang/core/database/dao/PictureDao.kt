package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.core.database.model.image.ReviewImageEntity;
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Query("""SELECT * 
                      FROM ReviewImageEntity 
                      WHERE reviewId = :reviewId""")           fun findByIdFlow(reviewId: Int): Flow<List<ReviewImageEntity>>
    @Query("""SELECT * 
                      FROM ReviewImageEntity 
                      WHERE reviewId = :reviewId""")   suspend fun findById(reviewId: Int): List<ReviewImageEntity>
    @Query("""SELECT * 
                      FROM ReviewImageEntity 
                      WHERE restaurantId = :restaurantId""")   suspend fun findByRestaurantId(restaurantId: Int): List<ReviewImageEntity>
    @Query(""" SELECT * 
                      FROM ReviewImageEntity 
                      Where restaurantId = (SELECT restaurantId 
                                            FROM ReviewImageEntity 
                                            WHERE pictureId = :pictureId)""")   suspend fun findAllRestaurantById(pictureId: Int): List<ReviewImageEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
                                suspend fun addAll(reviewImages: List<ReviewImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
                                suspend fun add(reviewImage: ReviewImageEntity)
    @Query("""DELETE 
                      FROM ReviewImageEntity 
                      WHERE reviewId = :reviewId""")    suspend fun delete(reviewId: Int)
    @Query("""DELETE 
                      FROM ReviewImageEntity""")    suspend fun deleteAll()
}