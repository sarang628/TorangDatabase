package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.ChatParticipants
import com.sarang.torang.core.database.model.chat.ChatRoom
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
import com.sarang.torang.core.database.model.chat.ChatRoomWithUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDao {
    @Query("""
        SELECT *
        FROM ChatRoomEntity
        ORDER BY createDate DESC
        """)
    fun findAll(): Flow<List<ChatRoom>>

    @Query("""
        SELECT c.*, (select count(*) from ChatParticipantsEntity where roomId = c.roomId) count
        FROM ChatRoomEntity c, ChatParticipantsEntity p
        Where 1=1
        and c.roomId = p.roomId
        and p.userId = :userId
        and count = 2
        ORDER BY createDate DESC
        """)
    suspend fun findByUserId(userId: Int): ChatRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(chatRoomEntity: List<ChatRoomEntity>)

    @Query("Delete from ChatRoomEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM ChatParticipantsEntity WHERE roomId = :roomId")
    suspend fun findByRoomId(roomId: Int): List<ChatParticipants>?

    @Query("""
    SELECT chatRoom.*, participants.*, user.*
    FROM ChatRoomEntity AS chatRoom
    JOIN ChatParticipantsEntity AS participants ON chatRoom.roomId = participants.roomId
    JOIN UserEntity AS user ON participants.userId = user.userId
    """)
    fun findAll1(): List<ChatRoomWithUsers>
}