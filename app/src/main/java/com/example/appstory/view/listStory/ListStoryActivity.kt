package com.example.appstory.view.listStory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.response.ListStoryItem
import com.example.appstory.adapter.ListStoryAdapter
import com.example.appstory.adapter.LoadingStateAdapter
import com.example.appstory.adapter.StoryAdapter
import com.example.appstory.databinding.ActivityListStoryBinding
import com.example.appstory.factory.ListStoryViewModelFactory
import com.example.appstory.view.AddStoryActivity
import com.example.appstory.view.DetailStory.DetailStoryActivity
import com.example.appstory.view.MainActivity
import com.example.appstory.view.MapsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private val StoryViewModel: ListStoryViewModel by viewModels {
        ListStoryViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("my_account", MODE_PRIVATE)

        setbtnaddstory()
        setPindahMap()
        settvname(sharedPreferences)
        setLogout(sharedPreferences)
        getData()
    }

    fun setbtnaddstory(){
        binding.btnAddStry.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }
    fun settvname(sharedPreferences: SharedPreferences){
        val name = sharedPreferences.getString("name","unkhown")
        binding.tvname.text = "Hello $name"
    }
    fun setLogout(sharedPreferences: SharedPreferences){
        binding.btnLogout.setOnClickListener {
            sharedPreferences.edit()
                .putBoolean("sesi", false)
                .putString("token", "unknown")
                .apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getData() {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        StoryViewModel.quote.observe(this, {
            adapter.submitData(lifecycle, it)
        })

        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: ListStoryItem) {
                val detailAct = Intent(this@ListStoryActivity, DetailStoryActivity::class.java)
                detailAct.putExtra("id","${data.id}")
                startActivity(detailAct)
            }
        })

    }

    private fun setPindahMap(){
        binding.btnMap.setOnClickListener {
            startActivity(Intent(this,MapsActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}

