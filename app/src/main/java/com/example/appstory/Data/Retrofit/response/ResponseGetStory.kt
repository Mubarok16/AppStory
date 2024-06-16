package com.example.appstory.Data.Retrofit.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story")
data class ListStoryItem(
	@PrimaryKey @field:SerializedName("id") val id: String,
	val createdAt: String,
	val description: String,
	val lat: Double,
	val lon: Double,
	val name: String,
	val photoUrl: String
)

data class ResponseGetStory(
// = emptyList()
	@field:SerializedName("listStory")
	val listStory: List<ListStory>,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)


data class ListStory(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: String? = null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: String? = null
)
