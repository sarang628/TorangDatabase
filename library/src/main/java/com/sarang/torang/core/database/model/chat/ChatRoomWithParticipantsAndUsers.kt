package com.sarang.torang.core.database.model.chat

data class ChatRoomWithParticipantsAndUsers(
    val chatRoomEntity: ChatRoomEntity,
    val participantsWithUsers: List<ParticipantsWithUser>,
)

data class ParticipantsWithUser(
    val roomId: Int,
    val userId: Int,
    val userName: String,
    val profilePicUrl: String,
)