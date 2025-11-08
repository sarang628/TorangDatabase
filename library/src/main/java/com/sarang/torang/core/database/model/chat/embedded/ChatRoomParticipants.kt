package com.sarang.torang.core.database.model.chat.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
data class ChatRoomParticipants(
    @Embedded val chatRoom: ChatRoomEntity,
    @Relation (parentColumn = "roomId", entityColumn = "roomId")
    val chatParticipants: List<ChatParticipantsEntity>
)