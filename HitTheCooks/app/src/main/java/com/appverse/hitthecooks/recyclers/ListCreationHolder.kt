package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.appverse.hitthecooks.R

/**
 * ViewHolder personalizado para el RecyclerView de creación de la lista, contiene los elementos personalizables del layout del Recycler
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
class ListCreationHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    /** Imagen de fondo de la lista **/
    val backgroundImage : ImageView by lazy { itemView.findViewById(R.id.backgroundImageShoppingList) }
    /** CardView soporte de la imagen **/
    val cardViewBackground : CardView by lazy { itemView.findViewById(R.id.cardViewBackground) }
    /** Animación a la hora de seleccionar la imagen **/
    val checkAnimation : LottieAnimationView by lazy { itemView.findViewById(R.id.checkAnimation) }

}