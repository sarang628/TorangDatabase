package com.sarang.torang

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.api.feed.ApiFeedV1
import com.sarang.torang.core.database.dao.FeedDao
import com.sarang.torang.core.database.dao.FeedGridDao
import com.sarang.torang.core.database.model.feed.FeedGridEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeedGridDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var feedGridDao: FeedGridDao
    @Inject lateinit var feedDao: FeedDao

    @Inject lateinit var feedV1: ApiFeedV1

    @Before fun setUp() { hiltRule.inject() }

    @Test fun addTest() = runTest{
        feedGridDao.add(FeedGridEntity(
            reviewId = 1,
            order = 1
        ))

        assertEquals(true, feedGridDao.findAll().size == 1)
    }

    @Test fun addAllTest() = runTest {
        feedGridDao.addAll(listOf(
            FeedGridEntity(
                reviewId = 1,
                order = 1
            ),
            FeedGridEntity(
                reviewId = 2,
                order = 1
            ),
            FeedGridEntity(
                reviewId = 3,
                order = 1
            )
        ))
        assertEquals(true, feedGridDao.findAllFlow().first().size == 3)
    }
}