package com.sarang.torang.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["keyword"], unique = true)])
data class SearchEntity(
    @PrimaryKey
    val key: Long = System.currentTimeMillis(),
    var keyword: String,
    var createDate: String = key.toString()
)