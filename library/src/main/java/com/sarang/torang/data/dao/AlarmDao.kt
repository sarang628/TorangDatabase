package com.sarang.torang.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.data.entity.AlarmAndUserEntity
import com.sarang.torang.data.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarms(users: List<AlarmEntity>)

    @Query("select * from AlarmEntity order by AlarmEntity.createDate desc")
    fun getAllAlarmData(): Flow<List<AlarmAndUserEntity>>
}