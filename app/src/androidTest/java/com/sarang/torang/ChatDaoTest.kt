package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sarang.torang.api.ApiChat
import com.sarang.torang.api.ApiLogin
import com.sarang.torang.core.database.dao.chat.ChatEntityWithUserDao
import com.sarang.torang.core.database.dao.chat.ChatImageDao
import com.sarang.torang.core.database.dao.chat.ChatMessageDao
import com.sarang.torang.core.database.dao.chat.ChatParticipantsDao
import com.sarang.torang.core.database.dao.chat.ChatRoomDao
import com.sarang.torang.core.database.dao.chat.ChatRoomWithParticipantsDao
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
import com.sarang.torang.core.database.model.chat.ParticipantsWithUserEntity
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
        val result = apiChat.getChatRoom(token)
        val rooms = result.map { ChatRoomEntity(it.roomId, it.createDate) }
        //chatRoomDao.addAll()
        chatRoomDao.addAll(rooms)

        // 채팅방 상태 리스트 넣기
        val flatUser = result.flatMap { room ->
            room.users.map {
                ChatParticipantsEntity(
                    userId = it.userId,
                    roomId = room.roomId
                )
            }
        }

        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(flatUser))
    }
}