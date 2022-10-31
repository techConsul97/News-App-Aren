package com.example.data.db

import androidx.room.*
import com.example.data.model.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(data: Data)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<Data>>

    @Delete
    suspend fun deleteNews(data: Data)
}