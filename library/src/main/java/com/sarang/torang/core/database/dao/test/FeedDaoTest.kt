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
import com.sarang.torang.core.database.dao.FavoriteDao
import kotlinx.coroutines.launch

@Composable
fun FavoriteDaoTest(feedDao: FavoriteDao) {
    var text by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Column {
        Button(onClick = {
            coroutineScope.launch {
                feedDao.getMyFavorite(1).collect {
                    text = "" + it
                }
            }
        }) {

        }
        Button(onClick = { feedDao.getFavorite(1) }) {

        }
        Text(text = text)
    }
}