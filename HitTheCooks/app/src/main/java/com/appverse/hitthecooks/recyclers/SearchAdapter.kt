package com.appverse.hitthecooks.recyclers

import android.app.Activity
import android.graphics.Color
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.interfaces.RecyclerTransferItem
import com.appverse.hitthecooks.model.Item
import com.bumptech.glide.Glide

/**
 * Adapter personalizado para el RecyclerView de la busqueda de alimentos
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 * @param context Contexto de la actividad donde mostrar el Recycler
 * @param items Coleccion de productos a mostrar
 */
class SearchAdapter(private val context: FoodList, private val items: ArrayList<Item>, private val activity: Activity): RecyclerView.Adapter<SearchHolder>() {

    private val transfer: RecyclerTransferItem by lazy { activity as RecyclerTransferItem }

    /**
     * Infla el layout del Recycler de busqueda de productos
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_food_list,parent,false)
        return SearchHolder(view)
    }

    /**
     * Da valor a los elementos del holder, que serán mostrados en el RecyclerView
     */
    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val animation : Animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.rotate_in)
        Glide.with(context).load(items[position].picUrl).into(holder.iconFood)
        holder.cardView.setCardBackgroundColor(activity.resources.getColor(R.color.green,activity.theme))
        holder.name.text = items[position].name
        holder.itemView.startAnimation(animation)
        holder.iconFood.setOnClickListener {
            transfer.passItem(items[position])
        }
    }

    /**
     * Devuelve el número de objetos del ArrayList de elementos
     */
    override fun getItemCount(): Int {
      return items.size
    }
}