package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

class ShoppingListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val cardViewList : CardView by lazy { itemView.findViewById(R.id.shoppingList) }
    val imageBackground : ImageView by lazy { itemView.findViewById(R.id.backgroundImageShoppingList) }
    val textViewShoppingListName : TextView by lazy { itemView.findViewById(R.id.shoppingListName) }
    val containerEmptyList : LinearLayout by lazy { itemView.findViewById(R.id.containerEmptyList) }
    val shareButton : CardView by lazy { itemView.findViewById(R.id.shareCardView) }
    val recyclerViewProfilePics : RecyclerView by lazy { itemView.findViewById(R.id.recyclerUserImageProfile) }
}