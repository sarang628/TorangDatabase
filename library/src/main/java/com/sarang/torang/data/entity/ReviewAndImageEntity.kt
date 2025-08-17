package com.sarang.torang.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

data class ReviewAndImageEntity(
    @Embedded val review: FeedEntity,
    @Relation(
        parentColumn = "reviewId",
        entityColumn = "reviewId"
    )
    val images: List<ReviewImageEntity>,
    @Relation(
        parentColumn = "reviewId",
        entityColumn = "reviewId"
    )
    val like: LikeEntity?,
    @Relation(
        parentColumn = "reviewId",
        entityColumn = "reviewId"
    )
    val favorite: FavoriteEntity?

)

fun ReviewAndImageEntity.toMap(): HashMap<String, RequestBody> {
    val params: HashMap<String, RequestBody> = HashMap()
    params["review_id"] =
        RequestBody.create("text/plain".toMediaTypeOrNull(), "" + review.reviewId)
    params["torang_id"] =
        RequestBody.create("text/plain".toMediaTypeOrNull(), "" + review.restaurantId)
    params["contents"] =
        RequestBody.create("text/plain".toMediaTypeOrNull(), "" + review.contents)
    params["rating"] = RequestBody.create("text/plain".toMediaTypeOrNull(), "" + review.rating)
    return params
}