package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.core.database.model.chat.embedded.ChatParticipantUser
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
import com.sarang.torang.core.database.model.chat.embedded.ChatRoomParticipants
import com.sarang.torang.core.database.model.chat.embedded.ChatRoomUser
import com.sarang.torang.core.database.model.user.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface ChatRoomDao {
    @Query("""
        SELECT *
        FROM ChatRoomEntity
        ORDER BY createDate DESC
        """)
    fun findAllFlow(): Flow<List<ChatRoomEntity>>

    @Query("""
        SELECT *
        FROM ChatRoomEntity
        ORDER BY createDate DESC
        """)
    fun findAllChatRoomParticipantsFlow(): Flow<List<ChatRoomParticipants>>

    @Query("""
        SELECT c.*, (select count(*) from ChatParticipantsEntity where roomId = c.roomId) count
        FROM ChatRoomEntity c, ChatParticipantsEntity p
        Where 1=1
        and c.roomId = p.roomId
        and p.userId = :userId
        and count = 2
        ORDER BY createDate DESC
        """)
    suspend fun findByUserId(userId: Int): ChatRoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(chatRoomEntity: List<ChatRoomEntity>)

    @Query("Delete from ChatRoomEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM ChatParticipantsEntity WHERE roomId = :roomId")
    suspend fun findByRoomId(roomId: Int): List<ChatParticipantUser>?

    fun findAllChatRoom(chatRoomDao: ChatRoomDao, userDao: UserDao) : Flow<List<ChatRoomUser>> {
        return chatRoomDao.findAllChatRoomParticipantsFlow().map {
            it.map {
                ChatRoomUser(
                    chatRoom = it.chatRoom,
                    chatParticipants = it.chatParticipants.map {
                        ChatParticipantUser(
                            participantsEntity = it,
                            userEntity = userDao.findById(it.userId)
                        )
                    }
                )
            }
        }
    }
}