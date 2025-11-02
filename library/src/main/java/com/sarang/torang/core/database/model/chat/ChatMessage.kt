package com.sarang.torang.core.database.model.chat

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.sarang.torang.core.database.model.user.UserEntity

@Entity
data class ChatMessage(
    @Embedded
    val chatEntity: ChatMessageEntity,
    @Relation(parentColumn = "userId", entityColumn = "userId")
    val userEntity: UserEntity,
    @Relation(parentColumn = "uuid", entityColumn = "parentUuid")
    val images: List<ChatImageEntity>,
)