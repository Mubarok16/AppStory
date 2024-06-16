package com.example.appstory.Data.di

import android.content.Context
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.RetrofitBuilder
import com.example.appstory.Data.database.StoryDatabase
import com.example.appstory.Data.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val sharedPreferences = context.getSharedPreferences("my_account", Context.MODE_PRIVATE)
        val database = StoryDatabase.getDatabase(context)
        val apiService = RetrofitBuilder(sharedPreferences).apiServiceAllStory
        return StoryRepository(database, apiService)
    }
}