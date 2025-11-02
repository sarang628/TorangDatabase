package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.core.database.model.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAllFlow(): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE userId IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun addAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("select * from UserEntity where userId = (:userId)")
    fun getUser(userId: Int): Flow<UserEntity>

    @Query("update UserEntity set userName = :userName ,profilePicUrl = :profilePicUrl  where userId = :userId")
    suspend fun updateUser(userId: Int, userName: String, profilePicUrl: String)

    @Query("SELECT COUNT(*) FROM UserEntity WHERE userId = :userId")
    suspend fun exists(userId: Int): Int

    @Query("update UserEntity set userName = :userName ,profilePicUrl = :profilePicUrl  where userId = :userId")
    suspend fun updateByChatRoom(userId: Int, userName: String, profilePicUrl: String)

    @Transaction
    suspend fun insertOrUpdateUser(users: List<UserEntity>) {
        users.forEach { user ->
            if (exists(user.userId) > 0) {
                updateByChatRoom(
                    user.userId,
                    user.userName,
                    user.profilePicUrl
                )
            } else {
                insertUser(
                    UserEntity(
                        userId = user.userId,
                        userName = user.userName,
                        email = "",
                        loginPlatform = "",
                        createDate = "",
                        accessToken = "",
                        profilePicUrl = user.profilePicUrl,
                        point = 0,
                        reviewCount = "",
                        followers = "",
                        following = ""
                    )
                )
            }
        }
    }
}