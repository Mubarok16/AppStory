package com.example.appstory.Data.Retrofit

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
    }
}
