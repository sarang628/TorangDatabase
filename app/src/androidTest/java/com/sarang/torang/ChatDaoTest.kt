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
import com.sarang.torang.core.database.model.chat.embedded.ChatRoomParticipants
import com.sarang.torang.di.torang_database_di.chatParticipantsEntityList
import com.sarang.torang.di.torang_database_di.chatRoomEntityList
import com.sarang.torang.di.torang_database_di.users
import com.sarang.torang.util.TorangRepositoryEncrypt
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
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
        userDao.addAll(chatRooms.users)
    }

    @Test
    fun findAllTest() = runTest{
        val roomsFlow = chatRoomDao.findAllFlow()
        val participantsFlow = chatParticipantsDao.findAllFlow()
        val userFlow = userDao.findAllFlow()

        val result = combine(
            roomsFlow,
            participantsFlow,
            userFlow
        ) { rooms, participants, users ->
            Triple(rooms, participants, users)
        }.filter { (rooms, participants, users) ->
                rooms.isNotEmpty() && participants.isNotEmpty() && users.isNotEmpty()
            }
            .map { (rooms, participants, users) ->
                rooms.map { room ->
                    val roomParticipants = participants.filter { it.roomId == room.roomId }
                    ChatRoomParticipants(
                        chatRoom = room,
                        chatParticipants = listOf()/*roomParticipants.map { p ->
                            ChatParticipantUser(
                                participantsEntity = p,
                                userEntity = users.find { it.userId == p.userId }
                            )
                        }*/
                    )
                }
            }
            .first()


        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(result))
    }

    @Test
    fun findAllChatRoomParticipantsFlowTest() = runTest{
        val roomsFlow = chatRoomDao.findAllChatRoomParticipantsFlow()
        val userFlow = userDao.findAllFlow()

        val result = combine(
            roomsFlow,
            userFlow
        ) { rooms, users ->
            Pair(rooms, users)
        }.filter { (rooms, users) ->
            rooms.isNotEmpty() && users.isNotEmpty()
        }
            .map { (rooms, users) ->
                rooms.map { room ->
                    val roomParticipants = room.chatParticipants.filter { it.roomId == room.chatRoom.roomId }
                    ChatRoomParticipants(
                        chatRoom = room.chatRoom,
                        chatParticipants = listOf()/*roomParticipants.map { p ->
                            ChatParticipantUser(
                                participantsEntity = p,
                                userEntity = users.find { it.userId == p.userId }
                            )
                        }*/
                    )
                }
            }
            .first()


        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(result))
    }
}