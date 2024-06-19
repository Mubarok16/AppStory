package com.example.appstory.Data.paging

import android.content.ContentValues
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.getAllStoryService
import com.example.appstory.Data.Retrofit.response.ListStoryItem
import com.example.appstory.Data.Retrofit.response.ResponseGetStory
import com.example.appstory.Data.Retrofit.response.ResponseStoryList
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoryPagingSource(private val apiService: getAllStoryService) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {

        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
//            CoroutineScope(Dispatchers.IO).launch {
//
//            }
            val responseData = apiService.getAllStoryService(page,params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}