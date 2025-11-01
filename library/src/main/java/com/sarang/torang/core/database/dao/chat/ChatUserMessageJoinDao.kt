package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.ChatEntityWithUser
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatUserMessageJoinDao {
    @Query("""
        SELECT *
        FROM ChatMessageEntity
        WHERE roomId = :roomId
        ORDER BY createDate DESC
        """)
    fun findByRoomId(roomId: Int): Flow<List<ChatEntityWithUser>>
}