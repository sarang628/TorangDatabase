package com.sarang.torang.core.database.model.chat.embedded

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity
import com.sarang.torang.core.database.model.user.UserEntity

/**
 * @param userId 채팅 상대방 userId
 */
@Entity
data class ChatParticipantUserNullable(
    @Embedded val participantsEntity: ChatParticipantsEntity,
    @Relation(parentColumn = "userId", entityColumn = "userId")
    val userEntity: UserEntity?
)