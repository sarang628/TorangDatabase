package com.sarang.torang.core.database.model.feed

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 메인화면에 피드 리스트 저장용 데이터
 */
@Entity
data class MainFeedEntity (
    @PrimaryKey
    val reviewId    : Int = -1,
    val order       : Int = 0
)