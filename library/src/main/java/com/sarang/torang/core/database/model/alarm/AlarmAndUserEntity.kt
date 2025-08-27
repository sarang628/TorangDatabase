package com.sarang.torang.core.database.model.alarm

import androidx.room.Embedded
import androidx.room.Relation
import com.sarang.torang.core.database.model.user.UserEntity

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