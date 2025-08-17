package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.sarang.torang.data.entity.FeedEntity
import com.sarang.torang.data.entity.ReviewImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyReviewDao {
    @Query("select * from ReviewImageEntity where reviewId = (:reviewId) order by ReviewImageEntity.createDate desc")
    fun getUploadedImage(reviewId: Int): Flow<List<ReviewImageEntity>>


    @Query("select * from FeedEntity where userId = (:userId) and restaurantId = (:restaurantId) order by FeedEntity.createDate desc")
    fun getMyReviews(userId: Int, restaurantId: Int): Flow<List<FeedEntity>>


    /*@Query(
        """
                select FeedData.review_id
                ,picture_url as reviewImageUrl
                ,rating
                ,contents
                ,FeedData.create_date as uploadDate
                ,max(ReviewImage.picture_id) as picture_id
                from FeedData left outer join ReviewImage   
                on FeedData.review_id = ReviewImage.review_id
                and FeedData.user_id = (:userId) and FeedData.restaurant_id = (:restaurantId)
                union
                select FeedData.review_id
                ,picture_url as reviewImageUrl
                ,rating
                ,contents
                ,FeedData.create_date as uploadDate
                ,ReviewImage.picture_id
                from FeedData left outer join ReviewImage   
                on FeedData.review_id = ReviewImage.review_id
                where picture_id is null
                and FeedData.user_id = (:userId) and FeedData.restaurant_id = (:restaurantId)
            """
    )
    fun getMyReviews2(userId: Int, restaurantId: Int): Flow<List<MyReviewItemUiState>>*/
}