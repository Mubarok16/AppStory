package com.example.appstory.view

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.RetrofitBuilder
import com.example.appstory.Data.Retrofit.response.ResponseGetStory
//import com.example.appstory.Data.Retrofit.response.ResponseGetStory
import com.example.appstory.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appstory.databinding.ActivityMapsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        ambilLokasi()
    }

    private fun ambilLokasi(){
        val sharedPreferences = getSharedPreferences("my_account", Context.MODE_PRIVATE)
        val token =  sharedPreferences.getString("token", "unknown")

        Log.d(TAG, "ambilLokasi: $token")

        val client = ApiConfig.getStory().getStoryService("Bearer $token")
        client.enqueue(object : Callback<ResponseGetStory> {
            override fun onResponse(
                call: Call<ResponseGetStory>,
                response: Response<ResponseGetStory>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.error == false ){
                            val dataList = responseBody.listStory
                            dataList.forEach { data ->
                                if (data.lat != null && data.lon != null){
                                    var lat = data.lat.toString().toDouble()
                                    var lon = data.lon.toString().toDouble()
                                    var latLng = LatLng(lat,lon)
                                    mMap.addMarker(MarkerOptions().position(latLng).title(data.name))
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                                    Log.d(TAG, "ambil Lokasi: $lat")
                                }
                            }
                        }
                    }
                } else {
                    Log.e(ContentValues.TAG, "gagal: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseGetStory>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}