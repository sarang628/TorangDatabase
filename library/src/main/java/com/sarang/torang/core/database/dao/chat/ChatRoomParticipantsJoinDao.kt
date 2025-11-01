package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.ChatRoomWithParticipantsEntity
import com.sarang.torang.core.database.model.chat.ParticipantsWithUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomParticipantsJoinDao {
    @Query("""
        SELECT *
        FROM ChatRoomEntity
        ORDER BY createDate DESC
        """)
    fun findAll(): Flow<List<ChatRoomWithParticipantsEntity>>

    @Query("SELECT * FROM ChatParticipantsEntity WHERE roomId = :roomId")
    fun findByRoomIdFlow(roomId: Int): Flow<List<ParticipantsWithUserEntity>?>

    @Query("SELECT * FROM ChatParticipantsEntity WHERE roomId = :roomId")
    suspend fun findByRoomId(roomId: Int): List<ParticipantsWithUserEntity>?

    @Query("""
        SELECT c.*, (select count(*) from ChatParticipantsEntity where roomId = c.roomId) count
        FROM ChatRoomEntity c, ChatParticipantsEntity p
        Where 1=1
        and c.roomId = p.roomId
        and p.userId = :userId
        and count = 2
        ORDER BY createDate DESC
        """)
    suspend fun findByUserId(userId: Int): ChatRoomWithParticipantsEntity?
}