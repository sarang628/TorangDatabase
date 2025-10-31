package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity

@Dao
interface ChatParticipantsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(list: List<ChatParticipantsEntity>)

    @Query("Delete from ChatParticipantsEntity")
    suspend fun deleteAll()
}