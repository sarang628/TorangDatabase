package com.sarang.torang.data.dao

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarang.torang.data.entity.FavoriteEntity
import com.sarang.torang.data.entity.ReviewAndImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Dao
interface FavoriteDao {

    @Query("select count(*) from FavoriteEntity where reviewId = (:reviewId)")
    suspend fun hasFavorite(reviewId: Int): Int


    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("select * from FavoriteEntity where reviewId = (:reviewId)")
    suspend fun getFavorite1(reviewId: Int): FavoriteEntity

    @Insert
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("select * from FavoriteEntity where reviewId = (:reviewId)")
    fun getFavorite(reviewId: Int): Flow<FavoriteEntity>

    @Query(
        """
        SELECT FeedEntity.*, UserEntity.profilePicUrl, UserEntity.userId
        FROM FeedEntity 
        JOIN UserEntity ON FeedEntity.userId =  UserEntity.userId
        LEFT OUTER JOIN RestaurantEntity ON FeedEntity.restaurantId = RestaurantEntity.restaurantId
        WHERE reviewId IN (Select reviewId from FavoriteEntity where userId = (:userId) )
        ORDER BY createDate DESC
        """
    )
    fun getMyFavorite(userId: Int): Flow<List<ReviewAndImageEntity>>

    @Query(
        """
        DELETE FROM FavoriteEntity
    """
    )
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(feedList: List<FavoriteEntity>)
}

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