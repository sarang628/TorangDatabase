package com.sarang.torang.data.entity

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

// 다른 곳에서 변환 하기
/*fun RestaurantEntity.toEntity() : Restaurant{
    return Restaurant(restaurantId = restaurantId, restaurantName = restaurantName, address = address, lat = lat, lon = lon, rating = rating, tel = tel, prices = prices, restaurantType = restaurantType, regionCode = regionCode, reviewCount = reviewCount, site = site, website = website, imgUrl1 = imgUrl1)
}*/
