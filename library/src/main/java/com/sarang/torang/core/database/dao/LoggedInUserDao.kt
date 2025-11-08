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
    fun getLoggedInUserFlow(): Flow<LoggedInUserEntity?>

    @Query("select * from LoggedInUserEntity")
    suspend fun getLoggedInUser(): LoggedInUserEntity?

    @Query("select userName from LoggedInUserEntity")
    fun getUserNameFlow(): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: LoggedInUserEntity)

    @Query("update LoggedInUserEntity set userName = :name ,profilePicUrl = :url")
    suspend fun update(name: String, url: String)

    @Query("delete from LoggedInUserEntity")
    suspend fun clear()

    @Query("SELECT EXISTS(SELECT 1 FROM LoggedInUserEntity)")
    fun isLoginFlow(): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM LoggedInUserEntity)")
    suspend fun isLogin(): Boolean
}