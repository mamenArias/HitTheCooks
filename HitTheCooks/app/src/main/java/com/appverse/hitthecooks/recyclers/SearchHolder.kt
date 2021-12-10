package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name : TextView by lazy { itemView.findViewById(R.id.nameFood) }
    val iconFood : ImageView by lazy { itemView.findViewById(R.id.imageFood) }
}