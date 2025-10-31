package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.api.ApiChat
import com.sarang.torang.api.ApiLogin
import com.sarang.torang.core.database.dao.chat.ChatEntityWithUserDao
import com.sarang.torang.core.database.dao.chat.ChatImageDao
import com.sarang.torang.core.database.dao.chat.ChatMessageDao
import com.sarang.torang.core.database.dao.chat.ChatParticipantsDao
import com.sarang.torang.core.database.dao.chat.ChatRoomDao
import com.sarang.torang.core.database.dao.chat.ChatRoomWithParticipantsDao
import com.sarang.torang.util.TorangRepositoryEncrypt
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ChatDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var chatRoomDao                    : ChatRoomDao
    @Inject lateinit var chatEntityWithUserDao          : ChatEntityWithUserDao
    @Inject lateinit var chatImageDao                   : ChatImageDao
    @Inject lateinit var chatMessageDao                 : ChatMessageDao
    @Inject lateinit var chatParticipantsDao            : ChatParticipantsDao
    @Inject lateinit var chatRoomWithParticipantsDao    : ChatRoomWithParticipantsDao
    @Inject lateinit var apiChat                        : ApiChat
    @Inject lateinit var login                          : ApiLogin
    @Inject lateinit var encrype                        : TorangRepositoryEncrypt
    @Before fun setUp() { hiltRule.inject() }

    var token = ""

    private val tag = "__ChatDaoTest"

    @Before
    fun before() = runTest{
        val result = login.emailLogin("sry_ang@naver.com", encrype.encrypt("Torang!234"))
        token = result.token
    }

    @Test
    fun insert() = runTest{
        var result = apiChat.getChatRoom(token)
        Log.d(tag, result.toString())
    }
}