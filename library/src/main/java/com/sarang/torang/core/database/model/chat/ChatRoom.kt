package com.sarang.torang.core.database.model.chat

/**
 * @param userId 채팅 상대방 userId
 */
data class ChatRoom(
    val chatRoomEntity: ChatRoomEntity,
    val chatParticipants: List<ChatParticipants>,
)