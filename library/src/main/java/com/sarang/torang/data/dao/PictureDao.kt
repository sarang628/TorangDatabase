package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.data.entity.ReviewImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Query("select * from ReviewImageEntity Where reviewId = :reviewId")
    fun getFeedImageFlow(reviewId: Int): Flow<List<ReviewImageEntity>>

    @Query("select * from ReviewImageEntity Where reviewId = :reviewId")
    suspend fun getFeedImage(reviewId: Int): List<ReviewImageEntity>

    @Query("select * from ReviewImageEntity Where restaurantId = :restaurantId")
    suspend fun getFeedImageByRestaurantId(restaurantId: Int): List<ReviewImageEntity>

    @Query("""select * 
              from ReviewImageEntity 
              Where restaurantId = (select restaurantId from ReviewImageEntity where pictureId = :pictureId)""")
    suspend fun getFeedImageByImageId(pictureId: Int): List<ReviewImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAll(plantList: List<ReviewImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictures(reviewImages: List<ReviewImageEntity>)

    @Query("delete from ReviewImageEntity Where reviewId = :reviewId")
    suspend fun removePicture(reviewId: Int)

    @Query("DELETE FROM ReviewImageEntity")
    suspend fun deleteAll()
}