package com.sarang.torang.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["favoriteId"], unique = true)])
data class FavoriteEntity(
    @PrimaryKey
    var reviewId: Int,
    var favoriteId: Int,
    var userId: Int,
    var createDate: String
)