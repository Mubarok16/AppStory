package com.example.appstory.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.response.FileResponse
import com.example.appstory.databinding.ActivityAddStoryBinding
import com.example.appstory.view.listStory.ListStoryActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var currentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("my_account", Context.MODE_PRIVATE)

        binding.bntGlry.setOnClickListener { ambilImage() }
        binding.btnCamera.setOnClickListener { bukaKamera() }
        binding.upload.setOnClickListener {
            val token:String = sharedPreferences.getString("token", "Unknown").toString()
            val desk:String = binding.textArea.text.toString()


            insertDb("Bearer $token",desk)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            currentUri = data?.data
            tampilImage()
        }
    }

    fun ambilImage(){
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    fun tampilImage(){
        binding.imgUpload.setImageURI(currentUri)
    }

    fun bukaKamera(){
        currentUri = getImageUri(this)
        launcherIntentCamera.launch(currentUri)

    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            tampilImage()
        }
    }


    fun insertDb(token:String, deskirpsi:String){


            var imgUri = currentUri ?:"".toUri()
            var fileimg = uriToFile(imgUri,this@AddStoryActivity).reduceFileImage()

            val requestImageFile = fileimg.asRequestBody("image/jpeg".toMediaType())
            val img = MultipartBody.Part.createFormData(
                "photo",
                fileimg.name,
                requestImageFile
            )
            val deskRequestBody = deskirpsi.toRequestBody("text/plain".toMediaType())

        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.uploadService()
                val successResponse = apiService.uploadImage(token,img,deskRequestBody)
                Log.d(TAG, "upload berhasil: ${successResponse.message}")
                withContext(Dispatchers.Main){
                    startActivity(Intent(this@AddStoryActivity, ListStoryActivity::class.java))
                    finish()
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, FileResponse::class.java)
                Log.d(TAG, "upload gagal: ${errorResponse.message}")
            }
        }
    }




}

