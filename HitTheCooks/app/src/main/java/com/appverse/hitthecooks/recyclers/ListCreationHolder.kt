package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.appverse.hitthecooks.R

class ListCreationHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    val backgroundImage : ImageView by lazy { itemView.findViewById(R.id.backgroundImageShoppingList) }
    val cardViewBackground : CardView by lazy { itemView.findViewById(R.id.cardViewBackground) }
    val checkAnimation : LottieAnimationView by lazy { itemView.findViewById(R.id.checkAnimation) }

}