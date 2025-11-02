package com.sarang.torang.core.database.model.chat.embedded

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.sarang.torang.core.database.model.chat.ChatImageEntity
import com.sarang.torang.core.database.model.chat.ChatMessageEntity
import com.sarang.torang.core.database.model.user.UserEntity

@Entity
data class ChatMessageUserImages(
    @Embedded
    val chatMessage: ChatMessageEntity,
    @Relation(parentColumn = "userId", entityColumn = "userId")
    val user: UserEntity,
    @Relation(parentColumn = "uuid", entityColumn = "parentUuid")
    val images: List<ChatImageEntity>,
)