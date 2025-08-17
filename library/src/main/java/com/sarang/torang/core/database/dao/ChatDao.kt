package com.sarang.torang.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sarang.torang.core.database.entity.ChatEntity
import com.sarang.torang.core.database.entity.ChatEntityWithUser
import com.sarang.torang.core.database.entity.ChatParticipantsEntity
import com.sarang.torang.core.database.entity.ChatRoomEntity
import com.sarang.torang.core.database.entity.ChatRoomWithParticipantsEntity
import com.sarang.torang.core.database.entity.ParticipantsWithUserEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ChatDao {
    @Query(
        """
        SELECT *
        FROM ChatRoomEntity
        ORDER BY createDate DESC
        """
    )
    fun getChatRoom(): Flow<List<ChatRoomWithParticipantsEntity>>


    @Query(
        """
        SELECT *
        FROM ChatRoomEntity
        ORDER BY createDate DESC
        """
    )
    fun getChatRoom1(): Flow<List<ChatRoomEntity>>

    @Query("SELECT * FROM ChatParticipantsEntity WHERE roomId = :roomId")
    fun getParticipantsWithUsersFlow(roomId: Int): Flow<List<ParticipantsWithUserEntity>?>

    @Query("SELECT * FROM ChatParticipantsEntity WHERE roomId = :roomId")
    suspend fun getParticipantsWithUsers(roomId: Int): List<ParticipantsWithUserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipats(list: List<ChatParticipantsEntity>)


    @Query(
        """
        SELECT *
        FROM ChatEntity
        WHERE roomId = :roomId
        ORDER BY createDate DESC
        """
    )
    fun getContents(roomId: Int): Flow<List<ChatEntityWithUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(chatRoomEntity: List<ChatRoomEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllChat(chatRoomEntity: List<ChatEntity>)

    @Query("Delete from ChatRoomEntity")
    suspend fun deleteAllChatRoom()

    @Query("Delete from ChatParticipantsEntity")
    suspend fun deleteAllParticipants()

    @Query("Delete from ChatEntity")
    suspend fun deleteAllChat()

    @Query(
        """
        SELECT c.*, (select count(*) from ChatParticipantsEntity where roomId = c.roomId) count
        FROM ChatRoomEntity c, ChatParticipantsEntity p
        Where 1=1
        and c.roomId = p.roomId
        and p.userId = :userId
        and count = 2
        ORDER BY createDate DESC
        """
    )
    suspend fun getChatRoomByUserId(userId: Int): ChatRoomWithParticipantsEntity?

    @Insert
    suspend fun addChat(chatEntity: ChatEntity)

    @Query("delete from chatentity where uuid = :uuid")
    suspend fun delete(uuid: String)

    @Transaction
    suspend fun insertParticipants(
        //result: List<ChatRoomApiModel>,
    ) {
        /*result.forEach { chatRoom ->
            insertParticipats(
                chatRoom.users.map { user ->
                    ChatParticipantsEntity(
                        roomId = chatRoom.roomId,
                        userId = user.userId
                    )
                }
            )
        }*/
    }

    //TODO:: ChatRoomApiModel 데이터 변경하기
    @Transaction
    suspend fun loadChatRoom(userDao: UserDao, /*result: List<ChatRoomApiModel>*/) {
        //deleteAllChatRoom()
        //deleteAllParticipants()
        //addAll(result.map { it.toChatRoomEntity() })
        //userDao.insertOrUpdateUser(result.flatMap { it.users })
        //insertParticipants(result)
    }

    @Query(
        """
            Insert into ChatImageEntity (
                                        'parentUuid', 
                                        'uuid', 
                                        'roomId', 
                                        'userId', 
                                        'localUri', 
                                        'url', 
                                        'createDate', 
                                        'uploadedDate', 
                                        'sending',
                                        'failed') 
            values (:parentUuid, :uuid, :roomId, :userId, :localUri, :url, :createDate, :uploadedDate, :sending, 0)
        """
    )
    suspend fun addImage(
        parentUuid: String,
        uuid: String,
        roomId: Int,
        userId: Int,
        localUri: String,
        url: String,
        createDate: String,
        uploadedDate: String,
        sending: Boolean,
    )

    @Transaction
    suspend fun addImage1(
        parentUuid: String,
        roomId: Int,
        userId: Int,
        createDate: String,
        uploadedDate: String,
        sending: Boolean,
        message: List<String>,
    ) {
        message.forEach {
            addImage(
                parentUuid,
                UUID.randomUUID().toString(),
                roomId,
                userId,
                it,
                "",
                createDate,
                uploadedDate,
                sending
            )
        }
    }

    @Query(
        """ Update ChatImageEntity
                set failed = 1 
                where 1=1
                and sending = 1
                and roomId = :roomId
                and localUri Not In (:list)
    """
    )
    suspend fun updateFailedSendImages(list: List<String>, roomId: Int)

}