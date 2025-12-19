package com.sarang.torang.core.database.model.feed

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedGridEntity(
    @PrimaryKey
    val reviewId        : Int       = -1,
    val order           : Int       = 0
)