package com.sarang.torang.core.database.model.favorite

data class FavoriteAndImageEntity(
    var favoriteId  : Int,
    var reviewId    : Int?      = null,
    var createDate  : String?   = null,
    val pictureId   : Int?      = null,
    val pictureUrl  : String?   = null,
    val width       : Int?      = null,
    val height      : Int?      = null,
)