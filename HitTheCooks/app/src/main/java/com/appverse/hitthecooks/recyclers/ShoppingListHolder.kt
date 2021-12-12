package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

/**
 * ViewHolder personalizado para el RecyclerView de las listas, contiene los elementos personalizables del layout del Recycler
 */
class ShoppingListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    /** CardView que contiene la lista **/
    val cardViewList : CardView by lazy { itemView.findViewById(R.id.shoppingList) }
    /** Imagen de fondo de la lista **/
    val imageBackground : ImageView by lazy { itemView.findViewById(R.id.backgroundImageShoppingList) }
    /** Titulo de la lista **/
    val textViewShoppingListName : TextView by lazy { itemView.findViewById(R.id.shoppingListName) }

    val containerEmptyList : LinearLayout by lazy { itemView.findViewById(R.id.containerEmptyList) }
    /** CardView de los usuarios **/
    val shareButton : CardView by lazy { itemView.findViewById(R.id.shareCardView) }
    /** RecyclerView con los usuarios que comparten la lista **/
    val recyclerViewProfilePics : RecyclerView by lazy { itemView.findViewById(R.id.recyclerUserImageProfile) }
}