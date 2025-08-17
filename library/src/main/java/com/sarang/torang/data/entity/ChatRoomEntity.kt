package com.sarang.torang.data.entity

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
