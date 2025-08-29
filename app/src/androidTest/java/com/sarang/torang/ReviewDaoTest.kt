package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.core.database.dao.RestaurantImageDao
import com.sarang.torang.core.database.model.image.RestaurantImageEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * runTest를 사용하면 코루틴 안에서 코드를 작성할 수 있다.
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ReviewDaoTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var restaurantImageDao: RestaurantImageDao
    @Before fun setUp() { hiltRule.inject() }

    @Test
    fun insertDeleteTest() = runTest {
        restaurantImageDao.insertAll(listOf(RestaurantImageEntity(pictureId = 0, restaurantId = 1, pictureUrl = "url", createDate = "1")))
        Log.d("__test", "after insert ${restaurantImageDao.getAll()}")
        assert(restaurantImageDao.getAll().isNotEmpty())
        restaurantImageDao.deleteAll()
        Log.d("__test", "after delete ${restaurantImageDao.getAll()}")
        assert(restaurantImageDao.getAll().isEmpty())
    }


}