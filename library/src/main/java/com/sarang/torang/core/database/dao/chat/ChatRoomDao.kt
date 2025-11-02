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
    SELECT  c.createDate    as room_createDate,
            c.roomId        as room_roomId,
            p._id           as participant__id, 
            p.roomId        as participant_roomId, 
            p.userId        as participant_userId,
            u.userId        as user_userId,
            u.userName      as user_userName,
            u.email         as user_email,
            u.loginPlatform as user_loginPlatform,
            u.createDate    as user_createDate,
            u.accessToken   as user_accessToken,
            u.profilePicUrl as user_profilePicUrl,
            u.point         as user_point,
            u.reviewCount   as user_reviewCount,
            u.followers     as user_followers,
            u.`following`   as user_following
    FROM ChatParticipantsEntity AS p
    LEFT OUTER JOIN ChatRoomEntity    AS c ON c.roomId = p.roomId 
    LEFT OUTER JOIN UserEntity        AS u ON p.userId = u.userId
    """)
    fun findAll1(): List<ChatRoomWithUsers>
}