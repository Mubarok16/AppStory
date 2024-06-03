package com.example.appstory.view

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.response.FileResponse
import com.example.appstory.databinding.ActivityRegisBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        val dataNme = binding.etname.text
        val dataEmail = binding.etEmail.text
        val dataPass = binding.etPass.text

        binding.btnRegis.setOnClickListener {
            if (dataNme.isNotEmpty() && dataEmail.isNotEmpty() && dataPass.isNotEmpty()){
                simpandata(dataNme, dataEmail, dataPass)

            }else{
                Toast.makeText(this, "data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun simpandata(name:Editable, email:Editable, pass:Editable){
        showLoading(true)
        val client = ApiConfig.registerService().buatAkun(name.toString(),email.toString(),pass.toString())
        client.enqueue(object : Callback<FileResponse> {
            override fun onResponse(
                call: Call<FileResponse>,
                response: Response<FileResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.error == false ){
                                Toast.makeText(this@RegisActivity, "Akun berhasil terdaftar", Toast.LENGTH_SHORT).show()
                            pinndahActivity()
                        }
                    }
                } else {
                    Log.e(ContentValues.TAG, "gagal: ${response.message()}")
                    Toast.makeText(this@RegisActivity, "akun gagal terdaftar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                showLoading(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun pinndahActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}