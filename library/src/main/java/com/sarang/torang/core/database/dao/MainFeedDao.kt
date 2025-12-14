package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sarang.torang.core.database.model.feed.MainFeedEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainFeedDao {
    @Query("""
        SELECT *
        FROM FeedEntity
        WHERE reviewId in (SELECT   reviewId 
                           FROM     MainFeedEntity)
    """)          fun findAllFlow() : Flow<List<ReviewAndImageEntity>>
    @Insert                     suspend fun addAll(list : List<MainFeedEntity>)
    @Insert                     suspend fun all(entity : MainFeedEntity)

    @Query("""DELETE
                      FROM MainFeedEntity""")    suspend fun deleteAll()

    @Query("""DELETE
                      FROM MainFeedEntity
                      WHERE reviewId = :reviewId""")    suspend fun deleteById(reviewId : Int)
}
