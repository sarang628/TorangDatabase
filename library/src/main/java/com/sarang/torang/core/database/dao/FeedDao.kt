package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.core.database.model.favorite.FavoriteAndImageEntity
import com.sarang.torang.core.database.model.favorite.FavoriteEntity

import com.sarang.torang.core.database.model.feed.FeedEntity
import com.sarang.torang.core.database.model.feed.FeedGridAndImageEntity
import com.sarang.torang.core.database.model.feed.FeedGridEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import com.sarang.torang.core.database.model.image.ReviewImageEntity;
import com.sarang.torang.core.database.model.like.LikeAndImageEntity
import com.sarang.torang.core.database.model.like.LikeEntity
import com.sarang.torang.core.database.model.restaurant.RestaurantEntity
import com.sarang.torang.core.database.model.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) @Transaction suspend fun addAll(feeds: List<FeedEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) @Transaction suspend fun add(feed: FeedEntity)
    @Query(""" DELETE 
            FROM FeedEntity""")                                      suspend  fun deleteAll()
    @Query("""
        DELETE 
        FROM FeedEntity 
        WHERE reviewId = (:reviewId)
        """)                                      suspend  fun deleteByReviewId(reviewId: Int) : Int
    @Query("""update 
        FeedEntity 
        set likeAmount = likeAmount + 1 
        where reviewId = (:reviewId)""")                                      suspend  fun addLikeCount(reviewId: Int)
    @Query("""update 
                      FeedEntity 
                      set likeAmount = likeAmount - 1 
                      where reviewId = (:reviewId)""")                                      suspend  fun subTractLikeCount(reviewId: Int)
}