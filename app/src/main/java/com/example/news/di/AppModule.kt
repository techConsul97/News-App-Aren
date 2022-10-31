package com.example.news.di

import android.content.Context
import androidx.room.Room
import com.example.data.api.Api
import com.example.data.api.ApiReferences.BASE_URL
import com.example.data.db.NewsDao
import com.example.data.db.NewsDatabase
import com.example.data.repo.Repository
import com.example.data.repo.RepositoryImp
import com.example.domain.use_case.NewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "News_db.db"
        ).build()

    @Provides
    @Singleton
    fun provideDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.getDao()

    @Provides
    @Singleton
    fun provideRepository(api: Api, newsDao: NewsDao): Repository =
        RepositoryImp(api, newsDao)

    @Provides
    @Singleton
    fun provideApiListUseCase(repository: Repository): NewsUseCases =
        NewsUseCases(repository)
}