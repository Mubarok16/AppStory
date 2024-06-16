package com.example.appstory.Data.Retrofit

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = preferences.getString("token","unknown")

        return if (!token.isNullOrEmpty()) {
            val authorized = original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(authorized)
        } else {
            chain.proceed(original)
        }
    }

}