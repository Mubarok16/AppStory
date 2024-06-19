package com.example.appstory.adapter

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appstory.Data.Retrofit.response.ListStoryItem
import com.example.appstory.R
import com.example.appstory.databinding.ListStoryRecycleviewBinding

class ListStoryAdapter(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<ListStoryAdapter.ListStoryHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClicked(data: ListStoryItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: ListStoryAdapter.OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListStoryHolder(private val binding: ListStoryRecycleviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem){
            val uri = Uri.parse(data.photoUrl)
            Glide.with(itemView.context)
                .load(uri)
                .error(R.drawable.icon_image)
                .into(binding.imgStry)
            binding.nameUser.text = data.name
            Log.d(TAG, "$uri")

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryHolder {
        return ListStoryHolder(ListStoryRecycleviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ListStoryHolder, position: Int) {
        var story = listStory[position]
        holder.bind(story)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(story) }
    }
}


