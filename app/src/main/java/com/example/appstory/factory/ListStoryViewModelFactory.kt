package com.example.appstory.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appstory.Data.Retrofit.getStoryService
import com.example.appstory.view.listStory.ListStoryViewModel

class ListStoryViewModelFactory(private val data: getStoryService): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListStoryViewModel::class.java)){
            return ListStoryViewModel(data) as T
        }

        throw IllegalArgumentException("Unknown View Model Class: "+ modelClass.name)
    }

}