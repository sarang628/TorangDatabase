package com.sarang.torang.data.entity

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