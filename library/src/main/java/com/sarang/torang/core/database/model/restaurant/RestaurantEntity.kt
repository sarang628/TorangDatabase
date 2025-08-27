package com.sarang.torang.core.database.model.restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RestaurantEntity(
    @PrimaryKey val restaurantId: Int,
    val restaurantName: String,
    val lat: Double,
    val lon: Double,
    val rating: Float,
    val tel: String,
    val prices: String,
    val restaurantType: String,
    val address: String,
    val regionCode: Int,
    val reviewCount: Int,
    val site: String,
    val website: String,
    val imgUrl1: String
)