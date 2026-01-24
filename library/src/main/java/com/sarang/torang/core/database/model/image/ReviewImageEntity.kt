package com.sarang.torang.core.database.model.image

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReviewImageEntity(
    @PrimaryKey val pictureId: Int,
    val restaurantId    : Int?      = null,
    val userId          : Int?      = null,
    val reviewId        : Int?      = null,
    val pictureUrl      : String?   = null,
    val createDate      : String?   = null,
    val menuId          : Int?      = null,
    val menu            : Int?      = null,
    val width           : Int?      = null,
    val height          : Int?      = null,
    val order           : Int?      = null
)