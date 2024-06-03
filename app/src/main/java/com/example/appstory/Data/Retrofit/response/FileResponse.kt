package com.example.appstory.Data.Retrofit.response

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String
)
