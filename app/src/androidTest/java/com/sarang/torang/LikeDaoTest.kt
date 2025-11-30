package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.sarang.torang.api.ApiChat
import com.sarang.torang.api.ApiLogin
import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.core.database.dao.chat.ChatImageDao
import com.sarang.torang.core.database.dao.chat.ChatMessageDao
import com.sarang.torang.core.database.dao.chat.ChatParticipantsDao
import com.sarang.torang.core.database.dao.chat.ChatRoomDao
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
import com.sarang.torang.core.database.model.chat.embedded.ChatRoomParticipants
import com.sarang.torang.core.database.model.chat.embedded.ChatRoomUser
import com.sarang.torang.core.database.model.like.LikeEntity
import com.sarang.torang.core.database.model.user.UserEntity
import com.sarang.torang.di.torang_database_di.chatParticipantsEntityList
import com.sarang.torang.di.torang_database_di.chatRoomEntityList
import com.sarang.torang.di.torang_database_di.users
import com.sarang.torang.util.TorangRepositoryEncrypt
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LikeDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var login                          : ApiLogin
    @Inject lateinit var encrypt                        : TorangRepositoryEncrypt
    @Inject lateinit var likeDao                        : LikeDao
    @Before fun setUp() { hiltRule.inject() }

    var token = ""

    private val tag = "__ChatDaoTest"

    @Test
    fun test() = runTest {
        likeDao.add(
            LikeEntity(
                likeId = 1,
                reviewId = 1,
                userId = 1,
                createDate = ""
            )
        )

        assertEquals(1 ,likeDao.getByReviewId(1)?.userId)

        likeDao.delete(1)

        assertEquals(null ,likeDao.getByReviewId(1))
    }

}