package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.api.feed.ApiFeedV1
import com.sarang.torang.core.database.dao.FavoriteDao
import com.sarang.torang.core.database.dao.FeedDao
import com.sarang.torang.core.database.dao.FeedGridDao
import com.sarang.torang.core.database.dao.FeedInsertDao
import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.core.database.dao.PictureDao
import com.sarang.torang.core.database.dao.ReviewAndImageDao
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.core.database.model.feed.FeedGridEntity
import com.sarang.torang.data.remote.response.FeedApiModel
import com.sarang.torang.di.torang_database_di.toFavoriteEntity
import com.sarang.torang.di.torang_database_di.toFeedEntity
import com.sarang.torang.di.torang_database_di.toLikeEntity
import com.sarang.torang.di.torang_database_di.toReviewImage
import com.sarang.torang.di.torang_database_di.toUserEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.collections.map

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeedDaoApITest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var feedDao: FeedDao
    @Inject lateinit var feedInsertDao: FeedInsertDao
    @Inject lateinit var reviewAndImageDao: ReviewAndImageDao
    @Inject lateinit var userDao: UserDao
    @Inject lateinit var likeDao: LikeDao
    @Inject lateinit var pictureDao: PictureDao
    @Inject lateinit var favoriteDao: FavoriteDao

    @Inject lateinit var feedGridDao: FeedGridDao
    @Inject lateinit var apiFeedV1: ApiFeedV1
    @Before fun setUp() { hiltRule.inject() }

    private val tag = "__FeedDaoTest"

    fun insertFeed(feedList : List<FeedApiModel>) = runTest{
        feedInsertDao.insertAllFeed(
            userDao         = userDao,
            likeDao         = likeDao,
            pictureDao      = pictureDao,
            favoriteDao     = favoriteDao,
            feedList        = feedList.map { it.toFeedEntity() },
            reviewImages    = feedList.map { it.pictures }.flatMap { it }.map { it.toReviewImage() },
            userList        = feedList.map { it.toUserEntity() },
            likeList        = feedList.filter { it.like != null }.map { it.like!!.toLikeEntity() },
            favorites       = feedList.filter { it.favorite != null }.map { it.favorite!!.toFavoriteEntity() },
            feedDao         = feedDao
        )
    }

    @Before
    fun before() = runTest{
        apiFeedV1.findByPage("", 0, 20)
    }

    @Test
    fun getAllFeedWithUserTest() = runTest {
        val result = reviewAndImageDao.findAllFlow()
            .filter { it.isNotEmpty() }
            .first() // 비어있지 않은 데이터가 나올 때까지 기다렸다가 바로 종료

        Log.d(tag, result.toString())
    }

    @Test
    fun getFeedByPictureIdTest() = runTest {
        val result = reviewAndImageDao.findByPictureIdFlow(1039).first()
        Log.d(tag, result.toString());
    }

    @Test fun findAllFeedDao() = runTest{
        feedGridDao.addAll(listOf(
            FeedGridEntity(reviewId = 634, order = 0)
        ))

        assertEquals(true, reviewAndImageDao.findAllByFeedGrid().first().isNotEmpty())
    }
}