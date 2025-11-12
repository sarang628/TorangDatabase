package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.sarang.torang.api.ApiChat
import com.sarang.torang.api.ApiLogin
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.core.database.dao.chat.ChatImageDao
import com.sarang.torang.core.database.dao.chat.ChatMessageDao
import com.sarang.torang.core.database.dao.chat.ChatParticipantsDao
import com.sarang.torang.core.database.dao.chat.ChatRoomDao
import com.sarang.torang.core.database.model.chat.ChatParticipantsEntity
import com.sarang.torang.core.database.model.chat.ChatRoomEntity
import com.sarang.torang.core.database.model.chat.embedded.ChatRoomParticipants
import com.sarang.torang.core.database.model.chat.embedded.ChatRoomUser
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
class ChatDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var chatRoomDao                    : ChatRoomDao
    @Inject lateinit var chatImageDao                   : ChatImageDao
    @Inject lateinit var chatMessageDao                 : ChatMessageDao
    @Inject lateinit var chatParticipantsDao            : ChatParticipantsDao
    @Inject lateinit var userDao                        : UserDao
    @Inject lateinit var apiChat                        : ApiChat
    @Inject lateinit var login                          : ApiLogin
    @Inject lateinit var encrypt                        : TorangRepositoryEncrypt
    @Before fun setUp() { hiltRule.inject() }

    var token = ""

    private val tag = "__ChatDaoTest"

    @Before
    fun before() = runTest{
        chatRoomDao.deleteAll()
    }

    suspend fun callApi(){
        val result = login.emailLogin("sry_ang@naver.com", encrypt.encrypt("Torang!234"))
        token = result.token

        val chatRooms = apiChat.getChatRoom(token)
        chatRoomDao.addAll(chatRooms.chatRoomEntityList)
        chatParticipantsDao.addAll(chatRooms.chatParticipantsEntityList)
        userDao.addAll(chatRooms.users)
    }

    private suspend fun addSingleRoom(){
        chatRoomDao.addAll(listOf(
            ChatRoomEntity(
                roomId = 1,
                createDate = "123"
            )
        ))
    }
    private suspend fun addParticipants(){
        chatParticipantsDao.addAll(listOf(
            ChatParticipantsEntity(
                _id = 0,
                roomId = 1,
                userId = 1
            )
        ))

        userDao.addUser(UserEntity(
            userId = 1,
            userName = ""
        ))
    }

    @Test
    fun addSingleRoomTest() = runTest{
        addSingleRoom()
        val chatRooms = chatRoomDao.findAllChatRoom(chatRoomDao, userDao)
        val result = chatRooms.first()
        assertEquals(1, result[0].chatRoom.roomId)
        assertEquals(1, result.size)
        assertEquals(0, result[0].chatParticipants.size)
    }

    @Test
    fun addSingleRoomAndParticipantsTest() = runTest{
        addSingleRoom()
        addParticipants()
        val chatRooms = chatRoomDao.findAllChatRoom(chatRoomDao, userDao)
        val result = chatRooms.first()
        assertEquals(1, result[0].chatRoom.roomId)
        assertEquals(1, result.size)
        assertEquals(1, result[0].chatParticipants.size)
    }

    @Test
    fun findAllChatRoomParticipantsFlowTest() = runTest{
        val roomsFlow = chatRoomDao.findAllChatRoom(chatRoomDao, userDao)

        val result = roomsFlow.first()

        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(result))
    }

    @Test
    fun deleteRoom() = runTest {
        addSingleRoom()
        addParticipants()
        var chatRooms : Flow<List<ChatRoomUser>> = chatRoomDao.findAllChatRoom(chatRoomDao, userDao)
        assertEquals(1, chatRooms.first()[0].chatRoom.roomId)
        chatRoomDao.deleteById(1)
        chatRooms = chatRoomDao.findAllChatRoom(chatRoomDao, userDao)
        assertEquals(emptyList<ChatRoomUser>(), chatRooms.first())
    }

    @Test
    fun deleteParticipantsByRoomId() = runTest {
        addSingleRoom()
        addParticipants()
        val chatRooms = chatRoomDao.findAllChatRoom(chatRoomDao, userDao)
        assertEquals(1, chatRooms.first()[0].chatParticipants.size)
        chatParticipantsDao.deleteByRoomId(1)
        assertEquals(0, chatRooms.first()[0].chatParticipants.size)
    }

}