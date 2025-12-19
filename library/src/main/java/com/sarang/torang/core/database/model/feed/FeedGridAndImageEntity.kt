package com.sarang.torang.core.database.model.feed

data class FeedGridAndImageEntity(
    var reviewId    : Int,
    var order       : Int?      = null,
    val pictureId   : Int?      = null,
    val pictureUrl  : String?   = null,
    val width       : Int?      = null,
    val height      : Int?      = null,
)