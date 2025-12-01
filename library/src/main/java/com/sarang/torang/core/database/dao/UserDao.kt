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
    @Query("""
        SELECT *
        FROM UserEntity
        """)                     fun findAllFlow()                  : Flow<List<UserEntity>>
    @Query("""SELECT *
                      FROM UserEntity""")             suspend fun findAll()                      : List<UserEntity>
    @Query("""SELECT * 
                      FROM UserEntity 
                      WHERE userId IN (:userIds)""")                     fun findAllById(userIds: IntArray) : List<UserEntity>
    @Query("""SELECT * 
                      FROM UserEntity 
                      WHERE userId = (:userId)""")                     fun findByIdFlow(userId: Int)      : Flow<UserEntity>
    @Query("""SELECT * 
                      FROM UserEntity 
                      WHERE userId = (:userId)""")             suspend fun findById(userId: Int)          : UserEntity?
    @Query("""UPDATE UserEntity 
                      SET userName = :userName ,profilePicUrl = :profilePicUrl  
                      WHERE userId = :userId""")    suspend fun update(userId: Int, userName: String, profilePicUrl: String)
    @Query("""SELECT COUNT(*)
                      FROM UserEntity 
                      WHERE userId = :userId""")      suspend fun exists(userId: Int): Int
    @Query("""UPDATE UserEntity 
                      SET userName = :userName ,profilePicUrl = :profilePicUrl  
                      WHERE userId = :userId""")    suspend fun updateByChatRoom(userId: Int, userName: String, profilePicUrl: String)
    @Delete                                suspend fun delete(user: UserEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE) @Transaction
                                           suspend fun addAll(users: List<UserEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
                                           suspend fun addUser(user: UserEntity)
                                           suspend fun insertOrUpdateUser(user: UserEntity) {
        if (exists(user.userId) > 0) {
            updateByChatRoom(userId = user.userId,
                             userName = user.userName,
                             profilePicUrl = user.profilePicUrl) }
        else {
            addUser(UserEntity(userId = user.userId,
                               userName = user.userName,
                               email = user.email,
                               loginPlatform = user.loginPlatform,
                               createDate = user.createDate,
                               accessToken = user.accessToken,
                               profilePicUrl = user.profilePicUrl,
                               point = 0,
                               reviewCount = user.reviewCount,
                               followers = user.following,
                               following = user.following)) }
    }
    @Transaction                           suspend fun insertOrUpdateUsers(users: List<UserEntity>) {
        users.forEach { user -> insertOrUpdateUser(user = user) } }
}