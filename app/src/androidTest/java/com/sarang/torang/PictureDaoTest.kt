package com.sarang.torang

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.core.database.dao.PictureDao
import com.sarang.torang.core.database.model.image.ReviewImageEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class PictureDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var pictureDao: PictureDao
    @Before fun setUp() { hiltRule.inject() }

    private val tag = "__FeedDaoLocalTest"


    @Test
    fun addTest() = runTest {
        pictureDao.add(ReviewImageEntity(
            pictureId = 0,
            pictureUrl = "pictureUrl",
            reviewId = 0,
            order = 10
        ))
    }

    @Test
    fun getTest() = runTest {
        pictureDao.add(ReviewImageEntity(
            pictureId = 10,
            pictureUrl = "pictureUrl",
            reviewId = 0,
            order = 4
        ))
        pictureDao.add(ReviewImageEntity(
            pictureId = 34,
            pictureUrl = "pictureUrl",
            reviewId = 0,
            order = 3
        ))
        pictureDao.add(ReviewImageEntity(
            pictureId = 1,
            pictureUrl = "pictureUrl",
            reviewId = 0,
            order = 10
        ))

        val result = pictureDao.findById(0)

        assertEquals(result.get(0).order, 3)
    }

}