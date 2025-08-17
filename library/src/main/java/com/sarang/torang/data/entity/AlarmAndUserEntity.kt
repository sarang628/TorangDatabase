package com.sarang.torang.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AlarmAndUserEntity(
    @Embedded val alarm: AlarmEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val user: UserEntity?,
    @Relation(
        parentColumn = "otherUserId",
        entityColumn = "userId"
    )
    val another: UserEntity?

)