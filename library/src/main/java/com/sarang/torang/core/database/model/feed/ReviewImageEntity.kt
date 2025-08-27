package com.sarang.torang.core.database.model.feed
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReviewImageEntity(
    @PrimaryKey val pictureId: Int,
    val restaurantId: Int,
    val userId: Int,
    val reviewId: Int,
    val pictureUrl: String,
    val createDate: String,
    val menuId: Int,
    val menu: Int,
    val width: Int,
    val height: Int,
) {
    companion object {
        fun uploadParam(path: String): ReviewImageEntity {
            return ReviewImageEntity(
                pictureId = -1,
                restaurantId = -1,
                reviewId = -1,
                pictureUrl = path,
                createDate = "",
                menuId = -1,
                menu = 0,
                userId = -1,
                width = 0,
                height = 0
            )
        }
    }
}