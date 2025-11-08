package com.sarang.torang.core.database.dao.test

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sarang.torang.core.database.dao.LoggedInUserDao

@Composable
fun TestLoggedInUserDao(loggedInUserDao: LoggedInUserDao) {
    var isLogin by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = "", block = {
        loggedInUserDao.getLoggedInUser()?.let {
            isLogin = true
        }
    })

    Column {
        HorizontalDivider(color = Color.LightGray)
        Text(text = "TestLoggedInUserDao", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "${isLogin}")
        HorizontalDivider(color = Color.LightGray)
    }
}