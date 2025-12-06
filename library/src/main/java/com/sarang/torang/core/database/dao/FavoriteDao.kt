package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.favorite.FavoriteEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("""
        SELECT * 
        FROM FavoriteEntity 
        WHERE reviewId = (:reviewId)
        """)                suspend fun findByReviewId(reviewId: Int): FavoriteEntity
    @Query("""
        SELECT * 
        FROM FavoriteEntity 
        WHERE reviewId = (:reviewId)
        """)                        fun findByReviewIdFlow(reviewId: Int): Flow<FavoriteEntity>
    @Query("""
        SELECT count(*) 
        FROM FavoriteEntity 
        WHERE reviewId = (:reviewId)
        """)         suspend fun hasFavorite(reviewId: Int): Boolean
    @Query("""
        DELETE
        FROM FAVORITEENTITY
        WHERE favoriteId = :favoriteId
    """)                  suspend fun delete(favoriteId: Int)
    @Insert                                   suspend fun add(favorite: FavoriteEntity)
    @Query("""
        DELETE 
        FROM FavoriteEntity
    """)                  suspend fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
                                                      fun insertAll(feedList: List<FavoriteEntity>)
}