package com.sarang.torang.core.database.model.chat

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.sarang.torang.core.database.model.user.UserEntity

/**
 * @param userId 채팅 상대방 userId
 */
@Entity
data class ChatParticipants(
    @Embedded val participantsEntity: ChatParticipantsEntity,
    @Relation(parentColumn = "userId", entityColumn = "userId")
    val userEntity: UserEntity?
)