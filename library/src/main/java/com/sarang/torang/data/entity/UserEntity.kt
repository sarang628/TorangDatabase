package com.sarang.torang.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder

@Entity
data class UserEntity(
    @PrimaryKey val userId: Int,
    val userName: String,
    val email: String,
    val loginPlatform: String,
    val createDate: String,
    val accessToken: String,
    val profilePicUrl: String,
    val point: Int,
    val reviewCount: String,
    val followers: String,
    val following: String
) {
    override fun equals(other: Any?): Boolean {
        if (other is UserEntity) {
            return userId == other.userId
        }
        return super.equals(other)
    }
    override fun toString(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this)
    }
}