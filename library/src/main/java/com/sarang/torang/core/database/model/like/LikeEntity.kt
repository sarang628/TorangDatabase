package com.sarang.torang.core.database.model.like

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikeEntity(
    @PrimaryKey
    var likeId      : Int,
    var reviewId    : Int,
    var createDate  : String?
)