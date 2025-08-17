package com.sarang.torang.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.sarang.torang.data.dao.AlarmDao
import com.sarang.torang.data.dao.ChatDao
import com.sarang.torang.data.dao.CommentDao
import com.sarang.torang.data.dao.FavoriteDao
import com.sarang.torang.data.dao.FeedDao
import com.sarang.torang.data.dao.LikeDao
import com.sarang.torang.data.dao.LoggedInUserDao
import com.sarang.torang.data.dao.MenuDao
import com.sarang.torang.data.dao.MyFeedDao
import com.sarang.torang.data.dao.MyReviewDao
import com.sarang.torang.data.dao.PictureDao
import com.sarang.torang.data.dao.RestaurantDao
import com.sarang.torang.data.dao.ReviewDao
import com.sarang.torang.data.dao.SearchDao
import com.sarang.torang.data.dao.SearchedRestaurantDao
import com.sarang.torang.data.dao.UserDao
import com.sarang.torang.data.entity.AlarmEntity
import com.sarang.torang.data.entity.ChatEntity
import com.sarang.torang.data.entity.ChatImageEntity
import com.sarang.torang.data.entity.ChatParticipantsEntity
import com.sarang.torang.data.entity.ChatRoomEntity
import com.sarang.torang.data.entity.CommentEntity
import com.sarang.torang.data.entity.FavoriteEntity
import com.sarang.torang.data.entity.FeedEntity
import com.sarang.torang.data.entity.LikeEntity
import com.sarang.torang.data.entity.LoggedInUserEntity
import com.sarang.torang.data.entity.MenuEntity
import com.sarang.torang.data.entity.MyFeedEntity
import com.sarang.torang.data.entity.RestaurantEntity
import com.sarang.torang.data.entity.ReviewImageEntity
import com.sarang.torang.data.entity.SearchEntity
import com.sarang.torang.data.entity.SearchedRestaurantEntity
import com.sarang.torang.data.entity.UserEntity
import com.sarang.torang.workers.PLANT_DATA_FILENAME
import com.sarang.torang.workers.SeedDatabaseWorker
import com.sarang.torang.workers.SeedDatabaseWorker.Companion.KEY_FILENAME
import kotlin.also
import kotlin.jvm.java
import kotlin.to


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
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                        .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
                        .build()
                    WorkManager.getInstance(context).enqueue(request)
                }
            }).build()
        }
    }
}
