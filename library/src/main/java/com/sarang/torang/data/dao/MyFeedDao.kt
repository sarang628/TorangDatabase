package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.data.entity.FavoriteEntity
import com.sarang.torang.data.entity.LikeEntity
import com.sarang.torang.data.entity.MyFeedEntity
import com.sarang.torang.data.entity.RestaurantEntity
import com.sarang.torang.data.entity.ReviewAndImageEntity
import com.sarang.torang.data.entity.ReviewImageEntity
import com.sarang.torang.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyFeedDao {
    @Query(
        """
        SELECT MyFeedEntity.reviewId,/*1*/
               MyFeedEntity.userId,/*2*/
               MyFeedEntity.restaurantId,/*3*/
               MyFeedEntity.userName,/*4*/
               MyFeedEntity.restaurantName,/*5*/
               MyFeedEntity.profilePicUrl,/*6*/
               MyFeedEntity.contents,/*7*/
               MyFeedEntity.rating,/*8*/
               MyFeedEntity.likeAmount,/*9*/
               MyFeedEntity.commentAmount,/*10*/
               MyFeedEntity.createDate,/*11*/ 
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
        """
    )
    fun getMyFeed(userId: Int): Flow<List<ReviewAndImageEntity>>

    @Query("select * from MyFeedEntity order by MyFeedEntity.createDate desc")
    fun getAllFeedWithUser(): Flow<List<ReviewAndImageEntity>>

    @Query("DELETE FROM MyFeedEntity where reviewId = (:reviewId)")
    suspend fun deleteFeed(reviewId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAll(plantList: List<MyFeedEntity>)

    @Transaction
    suspend fun insertAllFeed(
        feedList: List<MyFeedEntity>,
        pictureDao: PictureDao,
        reviewImages: List<ReviewImageEntity>,
        userDao: UserDao,
        userList: List<UserEntity>,
        likeDao: LikeDao,
        likeList: List<LikeEntity>,
        favoriteDao: FavoriteDao,
        favorites: List<FavoriteEntity>
    ) {
        pictureDao.insertAll(reviewImages)
        userDao.insertAll(userList)
        likeDao.insertLikes(likeList)
        favoriteDao.insertAll(favorites)
        //마지막에 안넣어주면 앱 강제종료
        insertAll(feedList)
    }

    @Query("DELETE FROM FeedEntity")
    suspend fun deleteAll()

    @Query("select * from MyFeedEntity where reviewId = (:reviewId) order by MyFeedEntity.createDate desc")
    fun getFeedFlow(reviewId: Int): Flow<ReviewAndImageEntity>

    @Query("select * from MyFeedEntity where reviewId = (:reviewId) order by MyFeedEntity.createDate desc")
    suspend fun getFeed(reviewId: Int): ReviewAndImageEntity

    @Transaction
    suspend fun deleteAllAndInsertAll(
        likeDao: LikeDao,
        feedDao: MyFeedDao,
        users: List<UserEntity>,
        reviewImages: List<ReviewImageEntity>,
        likes: List<LikeEntity>,
        restaurants: List<RestaurantEntity>,
        feedData: List<MyFeedEntity>,
        favorites: List<FavoriteEntity>,
        deleteLikes: List<LikeEntity>,
    ): Int {
        likeDao.deleteLikes(deleteLikes)
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

    @Query("DELETE FROM ReviewImageEntity where reviewId = (:reviewId)")
    suspend fun deletePicturesByReviewId(reviewId: Int)

    @Query("select * from ReviewImageEntity where reviewId = (:reviewId)")
    fun getReviewImages(reviewId: Int): Flow<List<ReviewImageEntity>>


    /*incoude like*/
    @Query(
        """
            Select * 
            From FeedEntity
        """
    )
    fun feedIncludeLike() {

    }

    @Query(
        """
        SELECT MyFeedEntity.reviewId,/*1*/
               MyFeedEntity.userId,/*2*/
               MyFeedEntity.restaurantId,/*3*/
               MyFeedEntity.userName,/*4*/
               MyFeedEntity.restaurantName,/*5*/
               MyFeedEntity.profilePicUrl,/*6*/
               MyFeedEntity.contents,/*7*/
               MyFeedEntity.rating,/*8*/
               MyFeedEntity.likeAmount,/*9*/
               MyFeedEntity.commentAmount,/*10*/
               MyFeedEntity.createDate,/*11*/ 
               UserEntity.profilePicUrl,
               UserEntity.userName, 
               UserEntity.userId, 
               RestaurantEntity.restaurantName, 
               RestaurantEntity.restaurantId
        FROM MyFeedEntity 
        JOIN UserEntity ON MyFeedEntity.userId =  UserEntity.userId
        LEFT OUTER JOIN RestaurantEntity ON MyFeedEntity.restaurantId = RestaurantEntity.restaurantId
        WHERE MyFeedEntity.userId = (SELECT userId FROM MyFeedEntity where reviewId = :reviewId)
        -- and MyFeedEntity.createDate > (SELECT createDate FROM MyFeedEntity where reviewId = :reviewId)
        ORDER BY MyFeedEntity.createDate DESC
        """
    )
    fun getMyFeedByReviewId(reviewId: Int): Flow<List<ReviewAndImageEntity>>
}