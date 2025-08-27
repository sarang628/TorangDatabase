package com.sarang.torang.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.sarang.torang.core.database.dao.AlarmDao
import com.sarang.torang.core.database.dao.ChatDao
import com.sarang.torang.core.database.dao.CommentDao
import com.sarang.torang.core.database.dao.FavoriteDao
import com.sarang.torang.core.database.dao.FeedDao
import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.core.database.dao.MenuDao
import com.sarang.torang.core.database.dao.MyFeedDao
import com.sarang.torang.core.database.dao.MyReviewDao
import com.sarang.torang.core.database.dao.PictureDao
import com.sarang.torang.core.database.dao.RestaurantDao
import com.sarang.torang.core.database.dao.ReviewDao
import com.sarang.torang.core.database.dao.SearchDao
import com.sarang.torang.core.database.dao.SearchedRestaurantDao
import com.sarang.torang.core.database.dao.UserDao

import com.sarang.torang.core.database.model.alarm.AlarmEntity
import com.sarang.torang.core.database.model.chat.ChatEntity
import com.sarang.torang.core.database.model.chat.ChatImageEntity
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
import com.sarang.torang.core.database.model.comment.CommentEntity
import com.sarang.torang.core.database.model.favorite.FavoriteEntity
import com.sarang.torang.core.database.model.feed.FeedEntity
import com.sarang.torang.core.database.model.feed.MyFeedEntity
import com.sarang.torang.core.database.model.feed.ReviewImageEntity
import com.sarang.torang.core.database.model.like.LikeEntity
import com.sarang.torang.core.database.model.restaurant.MenuEntity
import com.sarang.torang.core.database.model.restaurant.RestaurantEntity
import com.sarang.torang.core.database.model.restaurant.SearchedRestaurantEntity
import com.sarang.torang.core.database.model.search.SearchEntity
import com.sarang.torang.core.database.model.user.LoggedInUserEntity
import com.sarang.torang.core.database.model.user.UserEntity
import com.sarang.torang.workers.PLANT_DATA_FILENAME
import com.sarang.torang.workers.TorangDatabaseWorker

/**
 * The Room database for this app
 */
@Database(
    entities = [
        UserEntity::class,
        FeedEntity::class,
        ReviewImageEntity::class,
        LikeEntity::class,
        RestaurantEntity::class,
        MenuEntity::class,
        AlarmEntity::class,
        LoggedInUserEntity::class,
        SearchEntity::class,
        FavoriteEntity::class,
        CommentEntity::class,
        MyFeedEntity::class,
        ChatEntity::class,
        ChatRoomEntity::class,
        ChatParticipantsEntity::class,
        ChatImageEntity::class,
        SearchedRestaurantEntity::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun reviewDao(): ReviewDao
    abstract fun menuDao(): MenuDao
    abstract fun alarmDao(): AlarmDao
    abstract fun myReviewDao(): MyReviewDao
    abstract fun LoggedInUserDao(): LoggedInUserDao
    abstract fun searchDao(): SearchDao
    abstract fun pictureDao(): PictureDao
    abstract fun feedDao(): FeedDao
    abstract fun commentDao(): CommentDao
    abstract fun likeDao(): LikeDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun myFeedDao(): MyFeedDao
    abstract fun chatDao(): ChatDao
    abstract fun searchedRestaurantDao(): SearchedRestaurantDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                "DATABASE_NAME"
            ).addCallback(object : Callback() {}).build()
        }

        fun getTestFeedInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this)
            {
                instance ?: buildTestFeedDatabase(context).also { instance = it }
            }
        }

        private fun buildTestFeedDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                "DATABASE_NAME"
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val request = OneTimeWorkRequestBuilder<TorangDatabaseWorker>()
                        .setInputData(workDataOf(TorangDatabaseWorker.Companion.KEY_FILENAME to PLANT_DATA_FILENAME))
                        .build()
                    WorkManager.getInstance(context).enqueue(request)
                }
            }).build()
        }
    }
}