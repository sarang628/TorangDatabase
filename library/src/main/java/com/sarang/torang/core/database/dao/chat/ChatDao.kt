package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.core.database.model.chat.ChatMessageEntity
import com.sarang.torang.core.database.model.chat.ChatRoomEntity

@Dao
interface ChatDao {

    @Query("Delete from ChatMessageEntity")
    suspend fun deleteAllChat()

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addAllChat(chatRoomEntity: List<ChatMessageEntity>)

    @Insert
    suspend fun addChat(chatEntity: ChatMessageEntity)

    @Query("delete from chatentity where uuid = :uuid")
    suspend fun delete(uuid: String)


    suspend fun saveAllChatRoom(result: List<ChatRoomEntity>) {
        //deleteAllChatRoom()
        //deleteAllParticipants()
        //addAll(result)
    }

}