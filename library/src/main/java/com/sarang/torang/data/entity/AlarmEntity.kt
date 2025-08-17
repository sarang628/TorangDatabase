package com.sarang.torang.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmEntity(
    @PrimaryKey
    val alarmId: Int,
    var userId: Int,
    var otherUserId: Int,
    var contents: String,
    var alarmType: Int,
    var reviewId: Int,
    var createDate: String
)