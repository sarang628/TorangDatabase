package com.sarang.torang.core.database.model.chat.embedded

import com.sarang.torang.core.database.model.chat.ChatRoomEntity

data class ChatRoomUser(
    val chatRoom: ChatRoomEntity,
    val chatParticipants: List<ChatParticipantUserNullable>
)