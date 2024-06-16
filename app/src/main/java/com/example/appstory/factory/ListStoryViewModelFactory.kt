package com.example.appstory.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//import com.example.appstory.Data.Retrofit.getStoryService
import com.example.appstory.Data.di.Injection
import com.example.appstory.view.listStory.ListStoryViewModel
import android.content.Context


class ListStoryViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListStoryViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}