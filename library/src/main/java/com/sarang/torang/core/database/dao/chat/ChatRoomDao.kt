package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDao {
    @Query("""
        SELECT *
        FROM ChatRoomEntity
        ORDER BY createDate DESC
        """)
    fun findAll(): Flow<List<ChatRoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(chatRoomEntity: List<ChatRoomEntity>)

    @Query("Delete from ChatRoomEntity")
    suspend fun deleteAll()
}