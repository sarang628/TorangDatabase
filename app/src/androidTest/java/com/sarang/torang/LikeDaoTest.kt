package com.sarang.torang

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.api.ApiLogin
import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.core.database.model.like.LikeEntity
import com.sarang.torang.util.TorangRepositoryEncrypt
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
class LikeDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var login                          : ApiLogin
    @Inject lateinit var encrypt                        : TorangRepositoryEncrypt
    @Inject lateinit var likeDao                        : LikeDao
    @Before fun setUp() { hiltRule.inject() }

    var token = ""

    private val tag = "__ChatDaoTest"

    @Test
    fun test() = runTest {
        likeDao.add(
            LikeEntity(
                likeId = 1,
                reviewId = 1,
                createDate = ""
            )
        )

        assertEquals(1 ,likeDao.findByReviewId(1)?.reviewId)

        likeDao.delete(1)

        assertEquals(null ,likeDao.findByReviewId(1))
    }

}