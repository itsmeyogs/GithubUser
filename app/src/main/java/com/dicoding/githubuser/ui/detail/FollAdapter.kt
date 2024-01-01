package com.dicoding.githubuser.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ItemFollBinding

class FollAdapter:ListAdapter<ItemsItem, FollAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFollBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        Glide.with(holder.itemView)
            .load(user.avatarUrl)
            .into(holder.binding.imgPhotoFoll)
    }

    class MyViewHolder(val binding:ItemFollBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            binding.tvNameFoll.text = user.login
        }
    }

    companion object{
        val DIFF_CALLBACK = object :DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem==newItem
            }
        }
    }

}
