package com.sarang.torang.core.database.model.image

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RestaurantImageEntity(
    @PrimaryKey val pictureId: Int,
    val restaurantId: Int,
    val pictureUrl: String,
    val createDate: String,
)