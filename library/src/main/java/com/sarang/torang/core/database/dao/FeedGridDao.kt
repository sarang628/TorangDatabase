package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.feed.FeedGridEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedGridDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(feedGrid: FeedGridEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(feedGridList : List<FeedGridEntity>)
    @Query("""SELECT *
                      From FeedGridEntity""") suspend fun findAll()                  : List<FeedGridEntity>
    @Query("""SELECT *
                      From FeedGridEntity""")         fun findAllFlow()              : Flow<List<FeedGridEntity>>
    @Query("""SELECT * 
                      FROM FeedGridEntity
                      WHERE reviewId = :reviewId""") suspend fun findById(reviewId : Int)   : FeedGridEntity?
    @Query("""SELECT *
                      FROM FeedGridEntity
                      WHERE reviewId = :reviewId""")         fun findByIdFlow(reviewId:Int) : Flow<FeedGridEntity?>
    @Query("""DELETE 
                      FROM FeedGridEntity
                      WHERE reviewId = :reviewId""")   suspend fun deleteById(reviewId : Int)
    @Query("""DELETE 
                      FROM FeedGridEntity""")   suspend fun deleteAll()
}
