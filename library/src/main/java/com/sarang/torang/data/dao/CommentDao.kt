package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.data.entity.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query(
        """
        SELECT *
        FROM CommentEntity
        WHERE 1=1
        and reviewId = (:reviewId)
        and (parentCommentId == null or parentCommentId == 0)
        ORDER BY createDate DESC
        """
    )
    fun getComments(reviewId: Int): Flow<List<CommentEntity>>

    @Query(
        """
        SELECT *
        FROM CommentEntity
        WHERE 1=1
        and parentCommentId = (:commentId)
        ORDER BY createDate DESC
        """
    )
    suspend fun getReply(commentId: Int): List<CommentEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(list: List<CommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(commentData: CommentEntity)

    @Query("DELETE FROM CommentEntity")
    suspend fun clear()

    @Query(
        """
        UPDATE commententity SET commentId = (:commentId) 
                            , userId = (:userId)
                            , profilePicUrl = (:profilePicUrl)
                            , userName = (:userName)
                            , comment = (:comment)
                            , reviewId = (:reviewId)
                            , createDate = (:createDate)
                            , commentLikeId = (:commentLikeId)
                            , commentLikeCount = (:commentLikeCount)
                            , tagUserId = (:tagUserId)
                            , subCommentCount = (:subCommentCount)
                            , parentCommentId = (:parentCommentId)
                            , isUploading = false
                            WHERE commentId = (:updateId)
    """
    )
    suspend fun update(
        updateId: Int,
        commentId: Int,
        userId: Int,
        profilePicUrl: String,
        userName: String,
        comment: String,
        reviewId: Int,
        createDate: String,
        commentLikeId: Int? = null,
        commentLikeCount: Int = 0,
        tagUserId: Int? = null,
        subCommentCount: Int? = null,
        parentCommentId: Int? = null
    )

    suspend fun update(updateId: Int, commentEntity: CommentEntity) {
        update(
            updateId = updateId,
            commentId = commentEntity.commentId,
            userId = commentEntity.userId,
            profilePicUrl = commentEntity.profilePicUrl,
            userName = commentEntity.userName,
            comment = commentEntity.comment,
            reviewId = commentEntity.reviewId,
            createDate = commentEntity.createDate,
            commentLikeId = commentEntity.commentLikeId,
            commentLikeCount = commentEntity.commentLikeCount,
            tagUserId = commentEntity.tagUserId,
            subCommentCount = commentEntity.subCommentCount,
            parentCommentId = commentEntity.parentCommentId
        )
    }

}