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
        """)  suspend  fun find(reviewId: Int)                           : ReviewAndImageEntity?
    @Query("""
        SELECT *
        FROM FeedEntity
        ORDER BY FeedEntity.createDate DESC
        """)           fun findAllFlow()                                 : Flow<List<ReviewAndImageEntity>>
    @Query("""
        SELECT *
        FROM FeedEntity
        WHERE FeedEntity.userId = (:userId)
        ORDER BY FeedEntity.createDate DESC
        """)           fun findAllByUserIdFlow(userId: Int)              : Flow<List<ReviewAndImageEntity>>
    @Query("""
        SELECT * 
        FROM FeedEntity 
        WHERE restaurantId = (:restaurantId) 
        ORDER BY FeedEntity.createDate DESC
        """)           fun findAllByRestaurantIdFlow(restaurantId: Int)  : Flow<List<ReviewAndImageEntity>>
    @Query(""" SELECT * 
        FROM FeedEntity 
        WHERE reviewId = (:reviewId) 
        ORDER BY FeedEntity.createDate DESC """)           fun findByReviewIdFlow(reviewId: Int)             : Flow<ReviewAndImageEntity>
    @Query("""SELECT * 
              FROM FeedEntity 
              WHERE reviewId = (SELECT reviewId 
                                FROM ReviewImageEntity 
                                WHERE pictureId = :pictureId)""")           fun findByPictureIdFlow(pictureId: Int)           : Flow<ReviewAndImageEntity?>
    @Query("""
        SELECT 
               FeedEntity.*, 
               UserEntity.profilePicUrl, 
               UserEntity.userId
        FROM FavoriteEntity
        LEFT OUTER JOIN FeedEntity ON FeedEntity.reviewId = FavoriteEntity.reviewId
        LEFT OUTER JOIN UserEntity ON FeedEntity.userId =  UserEntity.userId
        LEFT OUTER JOIN RestaurantEntity ON FeedEntity.restaurantId = RestaurantEntity.restaurantId
        ORDER BY FavoriteEntity.createDate DESC
        """)             fun findAllByFavoriteFlow()                       : Flow<List<ReviewAndImageEntity>>
    @Query("""
        SELECT 
               FeedEntity.*, 
               UserEntity.profilePicUrl, 
               UserEntity.userId
        FROM LikeEntity
        LEFT OUTER JOIN FeedEntity ON FeedEntity.reviewId = LikeEntity.reviewId
        LEFT OUTER JOIN UserEntity ON FeedEntity.userId =  UserEntity.userId
        LEFT OUTER JOIN RestaurantEntity ON FeedEntity.restaurantId = RestaurantEntity.restaurantId
        ORDER BY LikeEntity.createDate DESC
        """)             fun findAllByLikeFlow()                           : Flow<List<ReviewAndImageEntity>>
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
        pictureDao.addAll(reviewImages)
        userDao.addAll(userList)
        likeDao.addAll(likeList)
        favoriteDao.addAll(favorites)
        //마지막에 안 넣어주면 앱 강제종료
        addAll(feedList)
    }
}