package com.example.appstory.view.listStory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstory.Data.Retrofit.ApiConfig
import com.example.appstory.Data.Retrofit.response.ListStoryItem
import com.example.appstory.adapter.ListStoryAdapter
import com.example.appstory.databinding.ActivityListStoryBinding
import com.example.appstory.factory.ListStoryViewModelFactory
import com.example.appstory.view.AddStoryActivity
import com.example.appstory.view.DetailStory.DetailStoryActivity
import com.example.appstory.view.MainActivity

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private lateinit var viewModel: ListStoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("my_account", Context.MODE_PRIVATE)

        setbtnaddstory()
        settvname(sharedPreferences)
        setLogout(sharedPreferences)
        getDatabaseListStory(sharedPreferences)
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

    fun getDatabaseListStory( sharedPreferences: SharedPreferences){
        showLoading(true)
        val token =  sharedPreferences.getString("token", "unknown")

            viewModel = ViewModelProvider(this,(ListStoryViewModelFactory(ApiConfig.getStory())))[ListStoryViewModel::class.java]
            viewModel.getListStory("Bearer $token")
            viewModel.story.observe(this) { stories ->
                setRecycleView(stories)
                showLoading(false)
            }
    }

    fun setRecycleView(Story: List<ListStoryItem>){

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        val adapter = ListStoryAdapter(Story)
        binding.rvStory.adapter = adapter

        adapter.setOnItemClickCallback(object :ListStoryAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: ListStoryItem) {
                val intent = Intent(this@ListStoryActivity, DetailStoryActivity::class.java)
                intent.putExtra("id","${data.id}")
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}

