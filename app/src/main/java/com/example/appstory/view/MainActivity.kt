package com.example.appstory.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.response.ResponseLogin
import com.example.appstory.databinding.ActivityMainBinding
import com.example.appstory.view.listStory.ListStoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        animasi()

        binding.btnregistrasi.setOnClickListener {
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
        }

        binding.btnlogin.setOnClickListener {
            val email = binding.etem.text.toString()
            val pw = binding.etpw.text.toString()
            if (email.isNotEmpty()){
                login(email,pw)
            }else{
                Toast.makeText(this, "isi email dan password $pw", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("my_account", Context.MODE_PRIVATE)
        val sesi:Boolean = sharedPreferences.getBoolean("sesi",false)
        if (sesi == true){
            startActivity(Intent(this, ListStoryActivity::class.java))
            finish()
        }
    }

    fun login(eml:String, pw:String){
        val sharedPreferences = getSharedPreferences("my_account", Context.MODE_PRIVATE)

        if (pw.length > 8 ){
            showLoading(true)
            val client = ApiConfig.loginService().login(eml,pw)
            client.enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Log.e(TAG, "bner: ${responseBody.loginResult?.name}")
                            if (responseBody.error == false ){
                                sharedPreferences.edit()
                                    .putString("name", responseBody.loginResult?.name)
                                    .putString("token", responseBody.loginResult?.token)
                                    .putBoolean("sesi", true)
                                    .apply()
                                pindahActivity()
//                                Toast.makeText(this@MainActivity, "${responseBody.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Log.e(TAG, "gagal: ${response.message()}")
                        Toast.makeText(this@MainActivity, "email dan password invalid", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    showLoading(false)
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    fun pindahActivity(){
        startActivity(Intent(this, ListStoryActivity::class.java))
        finish()
    }

    private fun animasi() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val iconLogin = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(iconLogin)
            startDelay = 500
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}