package com.sarang.torang.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

/**
 * @param userId 채팅 상대방 userId
 */
@Entity
data class ParticipantsWithUserEntity(
    @Embedded val participantsEntity: ChatParticipantsEntity,
    @Relation(parentColumn = "userId", entityColumn = "userId")
    val userEntity: UserEntity
)

fun ParticipantsWithUserEntity.toParticipantsWithUser(): ParticipantsWithUser = ParticipantsWithUser(
    roomId = this.participantsEntity.roomId,
    userId = this.userEntity.userId,
    userName = this.userEntity.userName,
    profilePicUrl = this.userEntity.profilePicUrl,
)