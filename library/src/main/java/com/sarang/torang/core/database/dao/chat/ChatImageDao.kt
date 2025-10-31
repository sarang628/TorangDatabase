package com.sarang.torang.core.database.dao.chat

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import java.util.UUID

@Dao
interface ChatImageDao {
    @Query("""
            Insert into ChatImageEntity (
                                        'parentUuid', 
                                        'uuid', 
                                        'roomId', 
                                        'userId', 
                                        'localUri', 
                                        'url', 
                                        'createDate', 
                                        'uploadedDate', 
                                        'sending',
                                        'failed') 
            values (:parentUuid, :uuid, :roomId, :userId, :localUri, :url, :createDate, :uploadedDate, :sending, 0)
        """)
    suspend fun add(
        parentUuid      : String,
        uuid            : String,
        roomId          : Int,
        userId          : Int,
        localUri        : String,
        url             : String,
        createDate      : String,
        uploadedDate    : String,
        sending         : Boolean,
    )

    @Transaction
    suspend fun addAll(
        parentUuid      : String,
        roomId          : Int,
        userId          : Int,
        createDate      : String,
        uploadedDate    : String,
        sending         : Boolean,
        message         : List<String>,
    ) {
        message.forEach {
            add(
                parentUuid      = parentUuid,
                uuid            = UUID.randomUUID().toString(),
                roomId          = roomId,
                userId          = userId,
                localUri        = it,
                url             = "",
                createDate      = createDate,
                uploadedDate    = uploadedDate,
                sending         = sending
            )
        }
    }

    @Query(""" Update ChatImageEntity
                set failed = 1 
                where 1=1
                and sending = 1
                and roomId = :roomId
                and localUri Not In (:list)
    """)
    suspend fun update(list: List<String>, roomId: Int)
}