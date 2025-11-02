package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.sarang.torang.api.ApiChat
import com.sarang.torang.api.ApiLogin
import com.sarang.torang.core.database.dao.chat.ChatImageDao
import com.sarang.torang.core.database.dao.chat.ChatMessageDao
import com.sarang.torang.core.database.dao.chat.ChatParticipantsDao
import com.sarang.torang.core.database.dao.chat.ChatRoomDao
import com.sarang.torang.di.torang_database_di.chatParticipantsEntityList
import com.sarang.torang.di.torang_database_di.chatRoomEntityList
import com.sarang.torang.util.TorangRepositoryEncrypt
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
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
    @Inject lateinit var chatImageDao                   : ChatImageDao
    @Inject lateinit var chatMessageDao                 : ChatMessageDao
    @Inject lateinit var chatParticipantsDao            : ChatParticipantsDao
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

        val chatRooms = apiChat.getChatRoom(token)
        chatRoomDao.addAll(chatRooms.chatRoomEntityList)
        chatParticipantsDao.addAll(chatRooms.chatParticipantsEntityList)
    }

    @Test
    fun findAllTest() = runTest{
        val list = chatRoomDao.findAll().first()
        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(list))
    }

    @Test
    fun chatRoomTest() = runTest {
        val participants = chatParticipantsDao.findAll()
        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(participants))
        val list1 = chatRoomDao.findAll1().first()
        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(list1))
    }
}