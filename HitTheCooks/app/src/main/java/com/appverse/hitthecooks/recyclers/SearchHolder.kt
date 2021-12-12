package com.appverse.hitthecooks.recyclers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R

/**
 * ViewHolder para el RecyclerView de los elementos que buscamos para añadir a la lista de la compra.
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 */
class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /** TextView con el nombre del alimento que buscamos **/
    val name : TextView by lazy { itemView.findViewById(R.id.nameFood) }
    /** ImageView con la imagen del alimento que buscamos **/
    val iconFood : ImageView by lazy { itemView.findViewById(R.id.imageFood) }

}