package com.sarang.torang.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @param userId 채팅 상대방 userId
 */
@Entity(indices = [Index(value = ["roomId", "userId"], unique = true)])
data class ChatParticipantsEntity(
    @PrimaryKey(autoGenerate = true) var _id: Int = 0,
    val roomId: Int,
    val userId: Int,
)