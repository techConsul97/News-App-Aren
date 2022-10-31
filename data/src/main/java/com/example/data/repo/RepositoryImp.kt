package com.example.data.repo

import androidx.lifecycle.LiveData
import com.example.data.api.Api
import com.example.data.db.NewsDao
import com.example.data.model.AllNewsList
import com.example.data.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RepositoryImp(
    private val api: Api,
    private val newsDao: NewsDao
) : Repository {


    override suspend fun getNews(
        page: Int,
        publishedBefore: String?
    ): Flow<Response<AllNewsList>> = flow {
        try {
            val response =  api.getAllNews(page = page, publishedBefore = publishedBefore)
            if(response.isSuccessful){
                emit(Response.success(response.body()))
            }else{
                emit(Response.error(404,"Error".toResponseBody()))
            }
        }catch (e:IOException){
            emit(Response.error(12002,"No internet connection".toResponseBody()))
        }catch (e:HttpException){
            emit(Response.error(404,"Error".toResponseBody()))
        }


    }


    override fun getSavedNews(): Flow<List<Data>> = newsDao.getAllNews()

    override suspend fun saveNews(data: Data) {
        newsDao.insertNews(data)
    }

    override suspend fun deleteNews(data: Data) {
        newsDao.deleteNews(data)
    }
}