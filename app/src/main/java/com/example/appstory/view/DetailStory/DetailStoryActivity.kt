package com.example.appstory.view.DetailStory

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.response.ResponseDetail
import com.example.appstory.R
import com.example.appstory.databinding.ActivityDetailStoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        getId()
        getDatabaseDetail()
    }
    private fun getId(){
        id = intent.getStringExtra("id",).toString()
    }

    fun getDatabaseDetail(){
        val sharedPreferences = getSharedPreferences("my_account", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("token","unknown")
        val client = ApiConfig.getDetailService().getDetailService("Bearer $token",id)
        client.enqueue(object : Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: Response<ResponseDetail>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.error == false ){
                            var name = responseBody.story?.name
                            var img = responseBody.story?.photoUrl
                            var desk = responseBody.story?.description
                            setView(name,img,desk)
                        }
                    }
                } else {
                    Log.e(ContentValues.TAG, "gagal: ${response.message()}")
                    Toast.makeText(this@DetailStoryActivity, "email dan password invalid", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })

    }

    fun setView(name:String?,imgUri:String?,desk:String?){
        binding.tvName.text = name
        binding.tvDesk.text = desk
        val uri = Uri.parse(imgUri)
        Glide.with(this)
            .load(uri)
            .fitCenter()
            .error(R.drawable.icon_image)
            .into(binding.imgstry)
    }
}