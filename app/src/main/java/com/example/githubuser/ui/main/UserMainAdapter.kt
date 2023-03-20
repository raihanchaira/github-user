package com.example.githubuser.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.model.UserModel

class UserMainAdapter (val listUser : List<UserModel>?): RecyclerView.Adapter<UserMainAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        val imgPhoto : ImageView = view.findViewById(R.id.img_item_photo)
        val tvName : TextView = view.findViewById(R.id.tv_item_name)
        val tvEmail : TextView = view.findViewById(R.id.tv_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int = listUser?.size?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser?.get(position)
        if (user != null) {
            holder.tvName.text = user.names
        }
        if (user != null) {
            holder.tvEmail.text = user.url
        }
        if (user != null) {
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .into(holder.imgPhoto)
        }
        holder.itemView.setOnClickListener {
            if (user != null) {
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data : UserModel)
    }
}