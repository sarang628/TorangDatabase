package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.favorite.FavoriteEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("select count(*) from FavoriteEntity where reviewId = (:reviewId)")
    suspend fun hasFavorite(reviewId: Int): Int

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("select * from FavoriteEntity where reviewId = (:reviewId)")
    suspend fun getFavorite1(reviewId: Int): FavoriteEntity

    @Insert
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("select * from FavoriteEntity where reviewId = (:reviewId)")
    fun getFavorite(reviewId: Int): Flow<FavoriteEntity>

    @Query("""
        SELECT FeedEntity.*, UserEntity.profilePicUrl, UserEntity.userId
        FROM FeedEntity 
        JOIN UserEntity ON FeedEntity.userId =  UserEntity.userId
        LEFT OUTER JOIN RestaurantEntity ON FeedEntity.restaurantId = RestaurantEntity.restaurantId
        WHERE reviewId IN (Select reviewId from FavoriteEntity where userId = (:userId) )
        ORDER BY createDate DESC
        """)
    fun getMyFavorite(userId: Int): Flow<List<ReviewAndImageEntity>>

    @Query("""
        DELETE FROM FavoriteEntity
    """
    )
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(feedList: List<FavoriteEntity>)
}