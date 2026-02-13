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
interface FeedInsertDao {
    @Transaction suspend fun deleteAllAndInsertAll(likeDao      : LikeDao,
                                                   feedDao      : FeedDao,
                                                   users        : List<UserEntity>,
                                                   reviewImages : List<ReviewImageEntity>,
                                                   likes        : List<LikeEntity>,
                                                   restaurants  : List<RestaurantEntity>,
                                                   feedData     : List<FeedEntity>,
                                                   favorites    : List<FavoriteEntity>,
                                                   deleteLikes  : List<LikeEntity>): Int {
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
    suspend fun insertUserAndPictureAndLikeAndRestaurantAndFeed(users: List<UserEntity>,
                                                                reviewImages: List<ReviewImageEntity>,
                                                                likes: List<LikeEntity>,
                                                                restaurants: List<RestaurantEntity>,
                                                                feedData: List<FeedEntity>,
                                                                favorites: List<FavoriteEntity>)

    @Transaction
    suspend fun insertAllFeed(feedList      : List<FeedEntity>,
                              pictureDao    : PictureDao,
                              reviewImages  : List<ReviewImageEntity>,
                              userDao       : UserDao,
                              userList      : List<UserEntity>,
                              likeDao       : LikeDao,
                              likeList      : List<LikeEntity>,
                              favoriteDao   : FavoriteDao,
                              favorites     : List<FavoriteEntity>,
                              feedDao       : FeedDao) {
        pictureDao.addAll(reviewImages)
        userDao.insertOrUpdateUsers(userList)
        likeDao.addAll(likeList)
        favoriteDao.addAll(favorites)
        //마지막에 안 넣어주면 앱 강제종료
        feedDao.addAll(feedList)
    }
}