package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.core.database.model.favorite.FavoriteEntity
import com.sarang.torang.core.database.model.feed.MyFeedEntity
import com.sarang.torang.core.database.model.feed.ReviewAndImageEntity
import com.sarang.torang.core.database.model.image.ReviewImageEntity;
import com.sarang.torang.core.database.model.like.LikeEntity
import com.sarang.torang.core.database.model.restaurant.RestaurantEntity
import com.sarang.torang.core.database.model.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyFeedDao {
    @Query("""
        SELECT MyFeedEntity.reviewId,
               MyFeedEntity.userId,
               MyFeedEntity.restaurantId,
               MyFeedEntity.userName,
               MyFeedEntity.restaurantName,
               MyFeedEntity.profilePicUrl,
               MyFeedEntity.contents,
               MyFeedEntity.rating,
               MyFeedEntity.likeAmount,
               MyFeedEntity.commentAmount,
               MyFeedEntity.createDate, 
               UserEntity.profilePicUrl,
               UserEntity.userName, 
               UserEntity.userId, 
               RestaurantEntity.restaurantName, 
               RestaurantEntity.restaurantId
        FROM MyFeedEntity 
        JOIN UserEntity ON MyFeedEntity.userId =  UserEntity.userId
        LEFT OUTER JOIN RestaurantEntity ON MyFeedEntity.restaurantId = RestaurantEntity.restaurantId
        WHERE MyFeedEntity.userId = (:userId)
        ORDER BY MyFeedEntity.createDate DESC
        """)            fun findByUserId(userId: Int): Flow<List<ReviewAndImageEntity>>
    @Query("""SELECT * 
                      FROM MyFeedEntity 
                      WHERE reviewId = (:reviewId) 
                      ORDER BY MyFeedEntity.createDate DESC""")                                 fun findByIdFlow(reviewId: Int): Flow<ReviewAndImageEntity>
    @Query("""SELECT * 
                      FROM MyFeedEntity 
                      ORDER BY MyFeedEntity.createDate DESC""")                                 fun findAllFlow(): Flow<List<ReviewAndImageEntity>>
    @Query("""SELECT * 
                      FROM MyFeedEntity 
                      WHERE reviewId = (:reviewId) 
                      ORDER BY MyFeedEntity.createDate DESC""")                         suspend fun findById(reviewId: Int): ReviewAndImageEntity
    @Query("""SELECT * 
                      FROM ReviewImageEntity 
                      WHERE reviewId = (:reviewId)""")                                 fun findImagesById(reviewId: Int): Flow<List<ReviewImageEntity>>
    @Query("""SELECT MyFeedEntity.reviewId,
                             MyFeedEntity.userId,
                             MyFeedEntity.restaurantId,
                             MyFeedEntity.userName,
                             MyFeedEntity.restaurantName,
                             MyFeedEntity.profilePicUrl,
                             MyFeedEntity.contents,
                             MyFeedEntity.rating,
                             MyFeedEntity.likeAmount,
                             MyFeedEntity.commentAmount,
                             MyFeedEntity.createDate, 
                             UserEntity.profilePicUrl,
                             UserEntity.userName, 
                             UserEntity.userId, 
                             RestaurantEntity.restaurantName, 
                             RestaurantEntity.restaurantId
                     FROM MyFeedEntity 
                     JOIN UserEntity ON MyFeedEntity.userId =  UserEntity.userId
                     LEFT OUTER JOIN RestaurantEntity ON MyFeedEntity.restaurantId = RestaurantEntity.restaurantId
                     WHERE MyFeedEntity.userId = (SELECT userId FROM MyFeedEntity where reviewId = :reviewId)
                     ORDER BY MyFeedEntity.createDate DESC""")            fun findUserFeedsByReviewId(reviewId: Int): Flow<List<ReviewAndImageEntity>>
    @Query("""DELETE 
                      FROM MyFeedEntity 
                      WHERE reviewId = (:reviewId)""")                           suspend fun deleteById(reviewId: Int): Int
    @Transaction @Insert(onConflict = OnConflictStrategy.REPLACE)
                                                       suspend fun addAll(plantList: List<MyFeedEntity>)
    @Query("""DELETE 
                      FROM FeedEntity""")                           suspend fun deleteAll()

    @Transaction
    suspend fun insertAllFeed(feedList      : List<MyFeedEntity>,
                              pictureDao    : PictureDao,
                              reviewImages  : List<ReviewImageEntity>,
                              userDao       : UserDao,
                              userList      : List<UserEntity>,
                              likeDao       : LikeDao,
                              likeList      : List<LikeEntity>,
                              favoriteDao   : FavoriteDao,
                              favorites     : List<FavoriteEntity>) {
        pictureDao.addAll(reviewImages)
        userDao.addAll(userList)
        likeDao.addAll(likeList)
        favoriteDao.addAll(favorites)
        //마지막에 안넣어주면 앱 강제종료
        addAll(feedList)
    }

    @Transaction
    suspend fun deleteAllAndInsertAll(likeDao: LikeDao,
                                      feedDao: MyFeedDao,
                                      users: List<UserEntity>,
                                      reviewImages: List<ReviewImageEntity>,
                                      likes: List<LikeEntity>,
                                      restaurants: List<RestaurantEntity>,
                                      feedData: List<MyFeedEntity>,
                                      favorites: List<FavoriteEntity>,
                                      deleteLikes: List<LikeEntity>): Int {
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
        feedData: List<MyFeedEntity>,
        favorites: List<FavoriteEntity>,
    )
}
