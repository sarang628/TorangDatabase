package com.sarang.torang.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

import java.text.SimpleDateFormat
import kotlin.random.Random

@Entity
data class CommentEntity(
    @PrimaryKey
    val commentId: Int = Random.nextInt(0,Integer.MAX_VALUE),
    val userId: Int,
    val profilePicUrl: String,
    val userName: String,
    val comment: String,
    val reviewId: Int,
    val createDate: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
    val commentLikeId: Int? = null,
    val commentLikeCount: Int = 0,
    val tagUserId: Int? = null,
    val subCommentCount: Int? = null,
    val parentCommentId: Int? = null,
    val isUploading: Boolean = false,
)

fun testCommentEntity(): CommentEntity {
    return CommentEntity(
        commentId = 0,
        comment = "",
        commentLikeCount = 0,
        commentLikeId = 0,
        createDate = "",
        profilePicUrl = "",
        reviewId = 0,
        userId = 0,
        userName = ""
    )
}