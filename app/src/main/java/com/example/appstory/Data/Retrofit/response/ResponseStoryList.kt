package com.example.appstory.Data.Retrofit.response

data class ResponseStoryList(
    val error: Boolean,
    val message: String,
    val listStory: List<ListStoryItem>
)