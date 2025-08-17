package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.data.entity.SearchedRestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchedRestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurant(restaurants: List<SearchedRestaurantEntity>)

    @Query("DELETE FROM SearchedRestaurantEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM SearchedRestaurantEntity")
    suspend fun getAll(): List<SearchedRestaurantEntity>

    @Query("select * from SearchedRestaurantEntity order by restaurantName desc")
    fun getRestaurant(): Flow<List<SearchedRestaurantEntity>>

    @Query("select * from SearchedRestaurantEntity order by restaurantName desc")
    suspend fun getRestaurantDistance(): List<SearchedRestaurantEntity>

    @Query("select * from SearchedRestaurantEntity Where restaurantId = :restaurantId")
    suspend fun getRestaurantByRestaurantId(restaurantId: Int): SearchedRestaurantEntity?
}