package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.data.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeletedRestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurant(restaurants: ArrayList<RestaurantEntity>)

    @Query("select * from RestaurantEntity order by restaurantName desc")
    fun getRestaurant(): Flow<List<RestaurantEntity>>

    @Query("select * from RestaurantEntity order by restaurantName desc")
    suspend fun getRestaurantDistance(): List<RestaurantEntity>


    @Query("select * from RestaurantEntity Where restaurantId = (SELECT restaurantId FROM FeedEntity WHERE reviewId = :reviewId)")
    fun getRestaurantByReviewId(reviewId: Int): Flow<RestaurantEntity>
}