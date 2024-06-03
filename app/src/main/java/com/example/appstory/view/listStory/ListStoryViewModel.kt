package com.example.appstory.view.listStory

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.getStoryService
import com.example.appstory.Data.Retrofit.response.ListStoryItem
import com.example.appstory.Data.Retrofit.response.ResponseGetStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListStoryViewModel(private val data: getStoryService): ViewModel() {
    private var _story = MutableLiveData<List<ListStoryItem>>()
    var story: LiveData<List<ListStoryItem>> = _story

    fun getListStory(token: String){
        try {
            var story= data.getStoryService(token)

            val client = ApiConfig.getStory().getStoryService(token)
            client.enqueue(object : Callback<ResponseGetStory> {
                override fun onResponse(
                    call: Call<ResponseGetStory>,
                    response: Response<ResponseGetStory>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            if (responseBody.error == false ){
                                _story.postValue(responseBody.listStory)
                            }
                        }
                    } else {
                        Log.e(TAG, "gagal: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseGetStory>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        } catch (e: Exception) {
            // Handle error (e.g., update _story with error state)
            Log.d(TAG, "bener: $e")
        }
    }
}