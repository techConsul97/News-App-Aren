package com.example.data.repo

import androidx.lifecycle.LiveData
import com.example.data.model.AllNewsList
import com.example.data.model.Data
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun getNews(page: Int, publishedBefore: String?): Flow<Response<AllNewsList>>

     fun getSavedNews(): Flow<List<Data>>

    suspend fun saveNews(data: Data)

    suspend fun deleteNews(data: Data)
}