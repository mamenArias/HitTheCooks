package com.appverse.hitthecooks.recyclers

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.Item
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * Adapter para el RecylerView de los alimentos a agregar a la lista de la compra.
 * @param activity Actividad donde se implementa el Recycler.
 * @param list ArrayList con los alimentos que se pueden agregar a la lista de la compra.
 */
class FoodListAdapter (val activity:Activity, val list:ArrayList<String>):RecyclerView.Adapter<FoodListHolder>(){

    private val db= FirebaseFirestore.getInstance()
    /**
     * Función que infla el layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListHolder {
        return FoodListHolder(activity.layoutInflater.inflate(R.layout.recycler_food_list, parent, false))
    }

    /**
     * Función que asigna valor a los elementos del ViewHolder, que se van a mostrar en el Recycler.
     */
    override fun onBindViewHolder(holder: FoodListHolder, position: Int) {
        val context: Context = holder.imageFood.context
        db.collection(FirestoreCollections.ITEMS).document(list[position]).get().addOnSuccessListener {
            Glide.with(context).load(it.get("picUrl")).circleCrop().into(holder.imageFood)
            holder.textFood.text = it.get("Name").toString()
        }
    }

    /**
     * Función que devuelve el número de objetos que tiene el ArrayList
     */
    override fun getItemCount(): Int {
        return list.size
    }
}