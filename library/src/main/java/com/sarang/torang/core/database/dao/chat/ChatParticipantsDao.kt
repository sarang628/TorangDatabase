package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.embedded.ChatParticipantUserNullable
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatParticipantsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(list: List<ChatParticipantsEntity>)

    @Query("Delete from ChatParticipantsEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM ChatParticipantsEntity WHERE roomId = :roomId")
    suspend fun findByRoomId(roomId: Int): List<ChatParticipantUserNullable>
    @Query("SELECT * FROM ChatParticipantsEntity")
    suspend fun findAll() : List<ChatParticipantsEntity>

    @Query("SELECT * FROM ChatParticipantsEntity")
    fun findAllFlow() : Flow<List<ChatParticipantsEntity>>
}