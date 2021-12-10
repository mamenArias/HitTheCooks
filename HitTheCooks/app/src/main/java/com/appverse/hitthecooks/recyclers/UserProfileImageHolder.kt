package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

class UserProfileImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userImageProfile : ImageView by lazy { itemView.findViewById(R.id.userImageProfile)  }

}