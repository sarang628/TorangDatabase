package com.sarang.torang.core.database.model.favorite

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["favoriteId"], unique = true)])
data class FavoriteEntity(
    @PrimaryKey
    var favoriteId: Int,
    var reviewId: Int,
    var userId: Int,
    var createDate: String
)