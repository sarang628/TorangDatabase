package com.sarang.torang.core.database.model.chat

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.sarang.torang.core.database.model.user.UserEntity

/**
 * @param userId 채팅 상대방 userId
 */
@Entity
data class ChatRoom(
    @Embedded val chatRoomEntity: ChatRoomEntity,
    @Relation(parentColumn = "roomId", entityColumn = "roomId")
    val participantsEntity: List<ChatParticipantsEntity>,
)

data class ChatRoomWithUsers(
    @Embedded val chatRoom: ChatRoomEntity,
    @Embedded val participants: ChatParticipantsEntity,
    @Embedded val user: UserEntity
)