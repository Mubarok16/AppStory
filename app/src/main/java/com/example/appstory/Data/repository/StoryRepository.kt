package com.example.appstory.Data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.appstory.Data.Retrofit.getAllStoryService
import com.example.appstory.Data.Retrofit.response.ListStoryItem
import com.example.appstory.Data.database.StoryDatabase
import com.example.appstory.Data.paging.StoryPagingSource

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: getAllStoryService){
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }


}