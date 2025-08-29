package com.sarang.torang.core.database.dao.test

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
import com.google.gson.GsonBuilder
import com.sarang.torang.core.database.dao.ReviewDao
import kotlinx.coroutines.launch

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