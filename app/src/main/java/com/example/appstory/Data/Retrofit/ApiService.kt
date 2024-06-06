package com.example.appstory.Data.Retrofit

import com.example.appstory.Data.Retrofit.response.FileResponse
import com.example.appstory.Data.Retrofit.response.ResponseDetail
import com.example.appstory.Data.Retrofit.response.ResponseGetStory
import com.example.appstory.Data.Retrofit.response.ResponseLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UploadService {
    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Header("Authorization")token: String?,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
        ): FileResponse
}

interface RegisterService{
    @FormUrlEncoded
    @POST("register")
    fun buatAkun(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<FileResponse>
}

interface LoginService{
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>
}

interface getStoryService{
    @GET("stories")
    fun getStoryService(
        @Header("Authorization") token: String
    ): Call<ResponseGetStory>
}

interface getDetailService{
    @GET("stories/{id}")
    fun getDetailService(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ):Call<ResponseDetail>
}


