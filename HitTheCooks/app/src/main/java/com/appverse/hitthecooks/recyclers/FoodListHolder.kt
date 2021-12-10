package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

/**
 * ViewHolder para el RecyclerView de los elementos a añadir a la lista de la compra.
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 */
class FoodListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    /**
     * ImageView con la imagen del alimento a agregar a la lista.
     */
    val imageFood:ImageView by lazy { itemView.findViewById(R.id.imageFood) }

    /**
     * TextView con el nombre del alimento a agregar a la lista.
     */
    val textFood:TextView by lazy { itemView.findViewById(R.id.nameFood) }

}