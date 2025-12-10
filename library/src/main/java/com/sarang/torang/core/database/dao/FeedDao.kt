package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.core.database.model.favorite.FavoriteAndImageEntity
import com.sarang.torang.core.database.model.favorite.FavoriteEntity

import com.sarang.torang.core.database.model.feed.FeedEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import com.sarang.torang.core.database.model.image.ReviewImageEntity;
import com.sarang.torang.core.database.model.like.LikeAndImageEntity
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
        SELECT f.favoriteId,
               f.reviewId,
               f.createDate,
               ri.pictureId,
               ri.pictureUrl,
               ri.width,
               ri.height
        FROM FavoriteEntity AS f
        LEFT JOIN ReviewImageEntity AS ri
               ON ri.pictureId = (
                   SELECT pictureId
                   FROM ReviewImageEntity
                   WHERE reviewId = f.reviewId
                   ORDER BY 
                     createDate IS NULL ASC,  -- NULL은 TRUE(1) → 마지막
                     createDate ASC           -- NULL이 아닌 경우 가장 오래된 것
                   LIMIT 1
               )
        ORDER BY f.createDate DESC;
        """)             fun findAllByFavoriteFlow()                       : Flow<List<FavoriteAndImageEntity>>
    @Query("""
        SELECT l.likeId,
               l.reviewId,
               l.createDate,
               ri.pictureId,
               ri.pictureUrl,
               ri.width,
               ri.height
        FROM LikeEntity AS l
        LEFT JOIN ReviewImageEntity AS ri
               ON ri.pictureId = (
                   SELECT pictureId
                   FROM ReviewImageEntity
                   WHERE reviewId = l.reviewId
                   ORDER BY 
                     createDate IS NULL ASC,  -- NULL은 TRUE(1) → 마지막
                     createDate ASC           -- NULL이 아닌 경우 가장 오래된 것
                   LIMIT 1
               )
        ORDER BY l.createDate DESC;
        """)             fun findAllByLikeFlow()                           : Flow<List<LikeAndImageEntity>>
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