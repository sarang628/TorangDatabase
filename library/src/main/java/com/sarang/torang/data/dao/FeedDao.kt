package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.data.entity.FavoriteEntity
import com.sarang.torang.data.entity.FeedEntity
import com.sarang.torang.data.entity.LikeEntity
import com.sarang.torang.data.entity.RestaurantEntity
import com.sarang.torang.data.entity.ReviewAndImageEntity
import com.sarang.torang.data.entity.ReviewImageEntity
import com.sarang.torang.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Query(
        """
        SELECT FeedEntity.reviewId,/*1*/
               FeedEntity.userId,/*2*/
               FeedEntity.restaurantId,/*3*/
               FeedEntity.userName,/*4*/
               FeedEntity.restaurantName,/*5*/
               FeedEntity.profilePicUrl,/*6*/
               FeedEntity.contents,/*7*/
               FeedEntity.rating,/*8*/
               FeedEntity.likeAmount,/*9*/
               FeedEntity.commentAmount,/*10*/
               FeedEntity.createDate,/*11*/ 
               UserEntity.profilePicUrl,
               UserEntity.userName, 
               UserEntity.userId, 
               RestaurantEntity.restaurantName, 
               RestaurantEntity.restaurantId
        FROM FeedEntity 
        JOIN UserEntity ON FeedEntity.userId =  UserEntity.userId
        LEFT OUTER JOIN RestaurantEntity ON FeedEntity.restaurantId = RestaurantEntity.restaurantId
        WHERE FeedEntity.userId = (:userId)
        ORDER BY FeedEntity.createDate DESC
        """
    )
    fun getMyFeed(userId: Int): Flow<List<ReviewAndImageEntity>>

    @Query(
        """
        SELECT *
        FROM FeedEntity
        ORDER BY FeedEntity.createDate DESC"""
    )
    fun getAllFeedWithUser(): Flow<List<ReviewAndImageEntity>>

    @Query(
        """
        DELETE 
        FROM FeedEntity 
        where reviewId = (:reviewId)"""
    )
    suspend fun deleteFeed(reviewId: Int): Int

    @Query("DELETE FROM FeedEntity")
    suspend fun deleteAll()

    @Query("select * from FeedEntity where reviewId = (:reviewId) order by FeedEntity.createDate desc")
    fun getFeedFlow(reviewId: Int): Flow<ReviewAndImageEntity>

    @Query("select * from FeedEntity where reviewId = (:reviewId) order by FeedEntity.createDate desc")
    suspend fun getFeed(reviewId: Int): ReviewAndImageEntity?

    @Query("DELETE FROM ReviewImageEntity where reviewId = (:reviewId)")
    suspend fun deletePicturesByReviewId(reviewId: Int)

    @Query(
        """
        select * 
        from ReviewImageEntity 
        where reviewId = (:reviewId)"""
    )
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
        select * 
        from FeedEntity 
        where restaurantId = (:restaurantId) order by FeedEntity.createDate desc"""
    )
    fun getFeedByRestaurantId(restaurantId: Int): Flow<List<ReviewAndImageEntity>>

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
        userDao.insertAll(userList)
        likeDao.insertLikes(likeList)
        favoriteDao.insertAll(favorites)
        //마지막에 안넣어주면 앱 강제종료
        insertAll(feedList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAll(plantList: List<FeedEntity>)

    @Query("update FeedEntity set likeAmount = likeAmount + 1 where reviewId = (:reviewId)")
    suspend fun addLikeCount(reviewId: Int)

    @Query("update FeedEntity set likeAmount = likeAmount - 1 where reviewId = (:reviewId)")
    suspend fun subTractLikeCount(reviewId: Int)
}