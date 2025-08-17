package com.sarang.torang.data.dao

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.data.entity.LoggedInUserEntity
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

@Composable
fun TestLoggedInUserDao(loggedInUserDao: LoggedInUserDao) {
    var isLogin by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = "", block = {
        loggedInUserDao.getLoggedInUser1()?.let {
            isLogin = true
        }
    })

    Column {
        HorizontalDivider(color = Color.LightGray)
        Text(text = "TestLoggedInUserDao", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "${isLogin}")
        HorizontalDivider(color = Color.LightGray)
    }
}