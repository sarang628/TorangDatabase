package com.sarang.torang.core.database.model.chat.embedded

import com.sarang.torang.core.database.model.chat.ChatRoomEntity
data class ChatRoomParticipants(
    val chatRoom: ChatRoomEntity,
    val chatParticipants: List<ChatParticipantUser>,
)