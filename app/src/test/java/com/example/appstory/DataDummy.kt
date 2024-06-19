package com.example.appstory

import com.example.appstory.Data.Retrofit.response.ListStoryItem
import com.example.appstory.Data.Retrofit.response.ResponseGetStory


object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "createdAt + $i",
                "description $i",
                0.0,
                0.0,
                "name $i",
                "photoUrl $i",
            )
            items.add(story)
        }
        return items
    }
}