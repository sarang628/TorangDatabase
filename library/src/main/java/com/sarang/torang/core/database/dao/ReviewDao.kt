package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sarang.torang.core.database.model.feed.FeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM FeedEntity")
    fun getReviews(): Flow<List<FeedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<FeedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: FeedEntity)

    @Query("""select * 
        from FeedEntity 
        where reviewId = (:reviewId) order by FeedEntity.createDate desc
        """)
    fun getFeedFlowbyReviewId(reviewId: Int): Flow<FeedEntity?>

    @Query("""select * 
        from FeedEntity 
        where reviewId = (:reviewId) order by FeedEntity.createDate desc
        """)
    suspend fun getFeedbyReviewId(reviewId: Int): FeedEntity

    @Update
    suspend fun updateById(feedEntity: FeedEntity)
}


// app
// -> update review : role by repository(handle api or local db) ok
// -> api ok
// -> api result not yet
// -> local db update
// -> update local review table
// -> update local picture table