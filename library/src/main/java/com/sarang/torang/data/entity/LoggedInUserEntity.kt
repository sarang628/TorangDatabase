package com.sarang.torang.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoggedInUserEntity(
    @PrimaryKey
    val userId: Int,
    val userName: String,
    val email: String?,
    val loginPlatform: String?,
    val createDate: String?,
    val profilePicUrl: String?
)