package com.sarang.torang.core.database.model.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @param userId 채팅 상대방 userId
 */
@Entity(indices = [Index(value = ["roomId", "userId"], unique = true)])
data class ChatParticipantsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var _id: Int = 0,
    val roomId: Int,
    val userId: Int,
)