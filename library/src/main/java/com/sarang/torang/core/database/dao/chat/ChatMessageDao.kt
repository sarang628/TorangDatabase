package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.embedded.ChatMessageUserImages
import com.sarang.torang.core.database.model.chat.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Insert
    suspend fun add(chatEntity: ChatMessageEntity)
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addAll(chatRoomEntity: List<ChatMessageEntity>)
    @Query("delete from ChatMessageEntity where uuid = :uuid")
    suspend fun delete(uuid: String)
    @Query("Delete from ChatMessageEntity")
    suspend fun deleteAll()
    @Query("""
        SELECT *
        FROM ChatMessageEntity
        WHERE roomId = :roomId
        ORDER BY createDate DESC
        """)
    fun findByRoomId(roomId: Int): Flow<List<ChatMessageUserImages>>
}