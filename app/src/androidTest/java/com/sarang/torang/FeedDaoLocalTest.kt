package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.sarang.torang.api.feed.ApiFeedV1
import com.sarang.torang.core.database.dao.FavoriteDao
import com.sarang.torang.core.database.dao.FeedDao
import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.core.database.dao.PictureDao
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.core.database.model.favorite.FavoriteEntity
import com.sarang.torang.core.database.model.feed.FeedEntity
import com.sarang.torang.core.database.model.image.ReviewImageEntity
import com.sarang.torang.core.database.model.like.LikeEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlinx.coroutines.flow.first

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeedDaoLocalTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var feedDao: FeedDao
    @Inject lateinit var userDao: UserDao
    @Inject lateinit var likeDao: LikeDao
    @Inject lateinit var pictureDao: PictureDao
    @Inject lateinit var favoriteDao: FavoriteDao
    @Inject lateinit var apiFeedV1: ApiFeedV1

    @Before fun setUp() { hiltRule.inject() }

    private val tag = "__FeedDaoLocalTest"


    @Test
    fun addTest() = runTest {
        feedDao.add(
            FeedEntity(
                reviewId        = 1,
                userId          = 1,
                restaurantId    = 1,
                userName        = "userName",
                restaurantName  = "restaurantName",
                profilePicUrl   = "profilePicUrl",
                contents        = "contents",
                rating          = 0f,
                likeAmount      = 0,
                commentAmount   = 0,
                createDate      = ""
            )
        )
    }

    @Test
    fun getTest() = runTest {
        addTest()

        assertEquals("contents", feedDao.find(1)?.review?.contents)
        assertEquals(true, feedDao.find(1)?.images?.isEmpty())
        assertEquals(null, feedDao.find(1)?.like)
        assertEquals(null, feedDao.find(1)?.favorite)
    }

    @Test
    fun deleteTest() = runTest {
        addTest()

        feedDao.deleteByReviewId(1)

        assertEquals(null,feedDao.find(1))
    }

    @Test
    fun flowTest() = runTest{
        // Given
        feedDao.add(
            FeedEntity(
                reviewId = 1,
                userId = 1,
                restaurantId = 1,
                userName = "userName",
                restaurantName = "restaurantName",
                profilePicUrl = "profilePicUrl",
                contents = "contents",
                rating = 0f,
                likeAmount = 0,
                commentAmount = 0,
                createDate = ""
            )
        )

        assertEquals(false , feedDao.findAllFlow().first().isEmpty())

        likeDao.add(LikeEntity(
            likeId = 1,
            reviewId = 1,
            userId = 1,
            createDate = ""
        ))

        assertEquals(1 , feedDao.findAllFlow().first()[0].like?.likeId)

    }

    @Test
    fun getAllFlowTest() = runTest {
        assertEquals(true, feedDao.findAllFlow().first().isEmpty())
    }

    @Test
    fun getAllByUserIdFlow() = runTest {
        assertEquals(true, feedDao.findAllByUserIdFlow(1).first().isEmpty())
    }

    @Test
    fun findAllByFavoriteFlow() = runTest {
        favoriteDao.add(FavoriteEntity(
            favoriteId = 1,
            reviewId = 0,
            createDate = ""
        ))

        pictureDao.add(ReviewImageEntity(
            pictureId = 0,
            pictureUrl = "pictureUrl",
            reviewId = 0,
            width = 1,
            height = 2
        ))

        pictureDao.add(ReviewImageEntity(
            pictureId = 1,
            pictureUrl = "pictureUrl",
            reviewId = 0,
            width = 1,
            height = 2
        ))

        val first = feedDao.findAllByFavoriteFlow().first()

        Log.d("__findAllByFavoriteFlow", GsonBuilder().setPrettyPrinting().create().toJson(first))

        assertEquals(true, first.isNotEmpty())
    }
}