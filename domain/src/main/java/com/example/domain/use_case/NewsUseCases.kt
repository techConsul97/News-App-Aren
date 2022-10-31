package com.example.domain.use_case

import android.util.Log
import com.example.data.model.Data
import com.example.data.repo.Repository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class NewsUseCases(
    private val repository: Repository
) {

   suspend fun getApiNewsUseCase(
        page: Int,
        publishedBefore: String? = null,
        loadedList: List<Data>
    ): Flow<Resource<List<Data>>> = flow {
        emit(Resource.Loading(loadedList))
        try {
            repository.getNews(page, publishedBefore).collect {
                if (it.isSuccessful) {
                    emit(Resource.Success(loadedList.plus(it.body()!!.data)))
                } else {
                    emit(Resource.Error(it.message()))
                }
            }
        } catch (e: Exception) {
            Log.d("Bug",e.localizedMessage ?: "eee")
            emit(Resource.Error("could not load online news"))
        }

    }

    fun getDatabaseNewsUseCase() = repository.getSavedNews()

    suspend fun insertNewsUseCase(news: Data) {
        repository.saveNews(news)
    }

    suspend fun deleteNewsUseCase(news: Data) {
        repository.deleteNews(news)
    }

}