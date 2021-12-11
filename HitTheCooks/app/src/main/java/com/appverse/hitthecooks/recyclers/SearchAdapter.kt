package com.appverse.hitthecooks.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.Item
import com.bumptech.glide.Glide

class SearchAdapter(private val context : Context,private val items : ArrayList<Item>): RecyclerView.Adapter<SearchHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_food_list,parent,false)
        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val animation : Animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.rotate_in)
        Glide.with(context).load(items[position].picUrl).into(holder.iconFood)
        holder.name.text = items[position].name
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int {
      return items.size
    }
}