package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.image.RestaurantImageEntity

@Dao
interface RestaurantImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images : List<RestaurantImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image : RestaurantImageEntity)

    @Query(""" DELETE FROM RestaurantImageEntity """)
    suspend fun deleteAll()

    @Query("""
        SELECT * FROM RestaurantImageEntity
    """)
    suspend fun getAll() : List<RestaurantImageEntity>
}