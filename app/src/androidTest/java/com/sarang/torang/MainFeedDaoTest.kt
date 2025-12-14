package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.sarang.torang.api.feed.ApiFeedV1
import com.sarang.torang.core.database.dao.FavoriteDao
import com.sarang.torang.core.database.dao.FeedDao
import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.core.database.dao.MainFeedDao
import com.sarang.torang.core.database.dao.PictureDao
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.core.database.model.feed.MainFeedEntity
import com.sarang.torang.di.torang_database_di.toFavoriteEntity
import com.sarang.torang.di.torang_database_di.toFeedEntity
import com.sarang.torang.di.torang_database_di.toLikeEntity
import com.sarang.torang.di.torang_database_di.toReviewImage
import com.sarang.torang.di.torang_database_di.toUserEntity
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
class MainFeedDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var mainFeedDao: MainFeedDao
    @Before fun setUp() { hiltRule.inject() }
    @Inject lateinit var feedDao: FeedDao
    @Inject lateinit var userDao: UserDao
    @Inject lateinit var likeDao: LikeDao
    @Inject lateinit var pictureDao: PictureDao
    @Inject lateinit var favoriteDao: FavoriteDao
    @Inject lateinit var apiFeedV1: ApiFeedV1

    private val tag = "MainFeedDaoTest"

    @Before
    fun before() = runTest{
        val feedList = apiFeedV1.findByPage("", 0, 20)
        feedDao.insertAllFeed(
            userDao         = userDao,
            likeDao         = likeDao,
            pictureDao      = pictureDao,
            favoriteDao     = favoriteDao,
            feedList        = feedList.map { it.toFeedEntity() },
            reviewImages    = feedList.map { it.pictures }.flatMap { it }.map { it.toReviewImage() },
            userList        = feedList.map { it.toUserEntity() },
            likeList        = feedList.filter { it.like != null }.map { it.like!!.toLikeEntity() },
            favorites       = feedList.filter { it.favorite != null }.map { it.favorite!!.toFavoriteEntity() }
        )
    }


    @Test
    fun addTest() = runTest {
        // API 로 데이터 20개 불러오기
        val feedList = apiFeedV1.findByPage("", 0, 20)

        // 20개 데이터 순서대로 넣기
        mainFeedDao.addAll(feedList.mapIndexed { index, model ->
            MainFeedEntity(reviewId = model.reviewId,
                           order = index )
        })

        val data = mainFeedDao.findAllFlow().first()

        // 20개 들어갔는지 확인
        assertEquals(true, data.size == 20)

        // 데이터 확인
        Log.d(tag, GsonBuilder().setPrettyPrinting().create().toJson(data))
    }

}