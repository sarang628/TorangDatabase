package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.core.database.model.favorite.FavoriteEntity

import com.sarang.torang.core.database.model.feed.FeedEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import com.sarang.torang.core.database.model.image.ReviewImageEntity;
import com.sarang.torang.core.database.model.like.LikeEntity
import com.sarang.torang.core.database.model.restaurant.RestaurantEntity
import com.sarang.torang.core.database.model.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Query(""" 
        SELECT * 
        FROM FeedEntity 
        WHERE reviewId = (:reviewId) 
        ORDER BY FeedEntity.createDate DESC
        """)  suspend  fun get(reviewId: Int)                           : ReviewAndImageEntity?
    @Query("""
        SELECT *
        FROM FeedEntity
        ORDER BY FeedEntity.createDate DESC
        """)           fun getAllFlow()                                 : Flow<List<ReviewAndImageEntity>>
    @Query("""
        SELECT *
        FROM FeedEntity
        WHERE FeedEntity.userId = (:userId)
        ORDER BY FeedEntity.createDate DESC
        """)           fun getAllByUserIdFlow(userId: Int)              : Flow<List<ReviewAndImageEntity>>
    @Query("""
        SELECT * 
        FROM FeedEntity 
        WHERE restaurantId = (:restaurantId) 
        ORDER BY FeedEntity.createDate DESC
        """)           fun getAllByRestaurantIdFlow(restaurantId: Int)  : Flow<List<ReviewAndImageEntity>>
    @Query(""" SELECT * 
        FROM FeedEntity 
        WHERE reviewId = (:reviewId) 
        ORDER BY FeedEntity.createDate DESC """)           fun getByReviewIdFlow(reviewId: Int)             : Flow<ReviewAndImageEntity>
    @Query("""SELECT * 
              FROM FeedEntity 
              WHERE reviewId = (SELECT reviewId 
                                FROM ReviewImageEntity 
                                WHERE pictureId = :pictureId)""")           fun getByPictureIdFlow(pictureId: Int)           : Flow<ReviewAndImageEntity?>
    @Query(""" DELETE 
            FROM FeedEntity""")    suspend  fun deleteAll()
    @Query("""
        DELETE 
        FROM FeedEntity 
        WHERE reviewId = (:reviewId)
        """)    suspend  fun deleteByReviewId(reviewId: Int)              : Int
    @Query("""update 
        FeedEntity 
        set likeAmount = likeAmount + 1 
        where reviewId = (:reviewId)""")    suspend  fun addLikeCount(reviewId: Int)
    @Query("""update 
                      FeedEntity 
                      set likeAmount = likeAmount - 1 
                      where reviewId = (:reviewId)""")    suspend  fun subTractLikeCount(reviewId: Int)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction                suspend fun addAll(feeds: List<FeedEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction                suspend fun add(feed: FeedEntity)

    @Transaction
    suspend fun deleteAllAndInsertAll(
        likeDao: LikeDao,
        feedDao: FeedDao,
        users: List<UserEntity>,
        reviewImages: List<ReviewImageEntity>,
        likes: List<LikeEntity>,
        restaurants: List<RestaurantEntity>,
        feedData: List<FeedEntity>,
        favorites: List<FavoriteEntity>,
        deleteLikes: List<LikeEntity>,
    ): Int {
        likeDao.deleteAll(deleteLikes)
        feedDao.deleteAll()
        insertUserAndPictureAndLikeAndRestaurantAndFeed(
            users,
            reviewImages,
            likes,
            restaurants,
            feedData,
            favorites
        )
        return 0
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAndPictureAndLikeAndRestaurantAndFeed(
        users: List<UserEntity>,
        reviewImages: List<ReviewImageEntity>,
        likes: List<LikeEntity>,
        restaurants: List<RestaurantEntity>,
        feedData: List<FeedEntity>,
        favorites: List<FavoriteEntity>,
    )

    @Transaction
    suspend fun insertAllFeed(
        feedList: List<FeedEntity>,
        pictureDao: PictureDao,
        reviewImages: List<ReviewImageEntity>,
        userDao: UserDao,
        userList: List<UserEntity>,
        likeDao: LikeDao,
        likeList: List<LikeEntity>,
        favoriteDao: FavoriteDao,
        favorites: List<FavoriteEntity>,
    ) {
        pictureDao.insertAll(reviewImages)
        userDao.addAll(userList)
        likeDao.addAll(likeList)
        favoriteDao.insertAll(favorites)
        //마지막에 안 넣어주면 앱 강제종료
        addAll(feedList)
    }
}