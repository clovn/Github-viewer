package com.example.githubviewer.di

import android.content.Context
import android.content.SharedPreferences
import com.example.githubviewer.data.GithubService
import com.example.githubviewer.data.SharedPrefDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private const val KEY = "TOKEN"

private val json = Json {
    ignoreUnknownKeys = true
}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideGithubService() : GithubService = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://api.github.com/")
            .build()
            .create(GithubService::class.java)

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPrefDataSource = SharedPrefDataSource(context)

}