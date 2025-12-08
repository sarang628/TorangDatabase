package com.sarang.torang

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.api.feed.ApiFeedV1
import com.sarang.torang.core.database.dao.FavoriteDao
import com.sarang.torang.core.database.dao.FeedDao
import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.core.database.dao.PictureDao
import com.sarang.torang.core.database.dao.UserDao
import com.sarang.torang.core.database.model.favorite.FavoriteEntity
import com.sarang.torang.core.database.model.feed.FeedEntity
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
class FavoriteDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var favoriteDao: FavoriteDao
    @Before fun setUp() { hiltRule.inject() }

    private val tag = "__FeedDaoLocalTest"


    @Test
    fun addTest() = runTest {
        favoriteDao.add(FavoriteEntity(
            favoriteId = 0,
            reviewId = 0,
            createDate = ""
        ))
    }

}