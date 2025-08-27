package com.sarang.torang.core.database.model.restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuEntity(
    @PrimaryKey
    val menuId: Int,
    val restaurantId: String,
    val menuName: String,
    val menuPrice: String,
    val menuPicUrl: String,
    val rating: Float
)