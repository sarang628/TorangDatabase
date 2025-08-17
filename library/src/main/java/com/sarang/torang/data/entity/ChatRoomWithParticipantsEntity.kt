package com.sarang.torang.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

/**
 * @param userId 채팅 상대방 userId
 */
@Entity
data class ChatRoomWithParticipantsEntity(
    @Embedded val chatRoomEntity: ChatRoomEntity,
    @Relation(parentColumn = "roomId", entityColumn = "roomId")
    val participantsEntity: List<ChatParticipantsEntity>,
)
