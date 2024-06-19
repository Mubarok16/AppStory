package com.example.appstory.Data.Retrofit

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.appstory.view.MapsActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig{
    companion object{

        private fun instanceRetrofit(): Retrofit{
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://story-api.dicoding.dev/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }

        fun uploadService(): UploadService {
            return instanceRetrofit().create(UploadService::class.java)
        }

        fun registerService(): RegisterService{
            return instanceRetrofit().create(RegisterService::class.java)
        }

        fun loginService(): LoginService{
            return instanceRetrofit().create(LoginService::class.java)
        }

        fun getStory(): getStoryService{
            return instanceRetrofit().create(getStoryService::class.java)
        }
        fun getDetailService(): getDetailService{
            return instanceRetrofit().create(getDetailService::class.java)
        }

        fun getAllStory(): getAllStoryService{
            return instanceRetrofit().create(getAllStoryService::class.java)
        }
    }
}

class RetrofitBuilder(private val dataStore: SharedPreferences) {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .addInterceptor(AuthInterceptor(dataStore))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiServiceAllStory: getAllStoryService = getRetrofit().create(getAllStoryService::class.java)

}
