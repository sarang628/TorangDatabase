package com.sarang.torang.core.database.model.feed

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.sarang.torang.core.database.model.image.ReviewImageEntity

@Entity
data class FeedAndReviewEntity(
    @Embedded                                                       val feed        : FeedEntity,
    @Relation(parentColumn = "reviewId", entityColumn = "reviewId") val reviewImages: List<ReviewImageEntity>
)