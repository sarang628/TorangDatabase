package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.feed.MainFeedEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainFeedDao {
    @Query(""" SELECT f.*
                       FROM FeedEntity f
                       JOIN MainFeedEntity m ON f.reviewId = m.reviewId
                       ORDER BY m.`order` ASC """)        fun findAllFlow(): Flow<List<ReviewAndImageEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE )
                                suspend fun addAll(list : List<MainFeedEntity>)
    @Insert                     suspend fun all(entity : MainFeedEntity)
    @Query("""DELETE
                      FROM MainFeedEntity""")    suspend fun deleteAll()
    @Query("""DELETE
                      FROM MainFeedEntity
                      WHERE reviewId = :reviewId""")    suspend fun deleteById(reviewId : Int)
}
