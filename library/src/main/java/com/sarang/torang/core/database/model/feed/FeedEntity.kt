package com.sarang.torang.core.database.model.feed

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedEntity(
    @PrimaryKey
    val reviewId        : Int = -1,
    val userId          : Int,
    val restaurantId    : Int?,
    val userName        : String,
    val restaurantName  : String?,
    val profilePicUrl   : String,
    val contents        : String,
    val rating          : Float,
    val likeAmount      : Int,
    val commentAmount   : Int,
    val createDate      : String
)