package com.sarang.torang.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @param sending 전송중
 */
@Entity(
    indices = [Index(value = ["uuid"], unique = true)]
)
data class ChatEntity(
    @PrimaryKey(autoGenerate = true) var _id: Int = 0,
    val uuid: String,
    val roomId: Int,
    val userId: Int,
    val message: String,
    val createDate: String,
    val sending: Boolean,
)
