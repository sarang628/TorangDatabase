package com.sarang.torang.data.dao

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.google.gson.GsonBuilder
import com.sarang.torang.data.entity.FeedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Dao
interface ReviewDao {
    @Query("SELECT * FROM FeedEntity")
    fun getReviews(): Flow<List<FeedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<FeedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: FeedEntity)

    @Query(
        """
        select * 
        from FeedEntity 
        where reviewId = (:reviewId) order by FeedEntity.createDate desc
        """
    )
    fun getFeedFlowbyReviewId(reviewId: Int): Flow<FeedEntity?>

    @Query(
        """
        select * 
        from FeedEntity 
        where reviewId = (:reviewId) order by FeedEntity.createDate desc
        """
    )
    suspend fun getFeedbyReviewId(reviewId: Int): FeedEntity

    @Update
    suspend fun updateById(feedEntity: FeedEntity)
}

@Composable
fun ReviewDaoTest(reviewDao: ReviewDao) {
    val coroutine = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    Column {
        Text(text = "ReviewDaoTest", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Button(onClick = {
            coroutine.launch {
                try {
                    val feed = reviewDao.getFeedbyReviewId(118)
                    text = GsonBuilder().setPrettyPrinting().create().toJson(feed)
                } catch (e: Exception) {
                    text = e.message.toString()
                }
            }
        }) {
            Text(text = "getFeedbyReviewId : 118")
        }
        Button(onClick = {
            coroutine.launch {
                reviewDao.updateById(
                    reviewDao.getFeedbyReviewId(110).copy(
                        contents = "abcde"
                    )
                )
            }
        }) {
            Text(text = "updateReview")
        }
        Text(text = text)
    }
}


// app
// -> update review : role by repository(handle api or local db) ok
// -> api ok
// -> api result not yet
// -> local db update
// -> update local review table
// -> update local picture table