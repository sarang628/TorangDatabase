package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.user.LoggedInUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoggedInUserDao {
    @Query("select * from LoggedInUserEntity")
    fun getLoggedInUser(): Flow<LoggedInUserEntity?>

    @Query("select * from LoggedInUserEntity")
    suspend fun getLoggedInUser1(): LoggedInUserEntity?

    @Query("select userName from LoggedInUserEntity")
    fun getUserName(): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: LoggedInUserEntity)

    @Query("update LoggedInUserEntity set userName = :name ,profilePicUrl = :url")
    suspend fun update(name: String, url: String)

    @Query("delete from LoggedInUserEntity")
    suspend fun clear()

    @Query("select COUNT(*) from LoggedInUserEntity")
    fun isLogin(): Flow<Int>
}