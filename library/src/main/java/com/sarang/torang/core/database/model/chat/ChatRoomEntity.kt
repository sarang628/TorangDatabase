package com.sarang.torang.core.database.model.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param userId 채팅 상대방 userId
 */
@Entity
data class ChatRoomEntity(
    @PrimaryKey
    val roomId: Int,
    val createDate: String
)