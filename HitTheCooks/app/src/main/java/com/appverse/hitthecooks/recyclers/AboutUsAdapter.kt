package com.appverse.hitthecooks.recyclers

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.model.Student
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * Adapter personalizado para el RecyclerView de alumnos
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 * @param activity Actividad donde se implementa el RecyclerView
 * @param list ArrayList con los alumnos a introducir en el RecyclerView
 */
class AboutUsAdapter(val activity: Activity, val list: ArrayList<Student>) :
    RecyclerView.Adapter<AboutUsHolder>() {

    /** Objeto que contiene la instancia a la base de datos de Firebase **/
    val db= FirebaseFirestore.getInstance()

    /**
     * Infla el layout del Recycler sobre nosotros
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutUsHolder {
        return AboutUsHolder(
            activity.layoutInflater.inflate(
                R.layout.recycler_about_us, parent, false
            )
        )
    }

    /**
     * Da valor a los elementos del holder, que serán mostrados en el RecyclerView
     */
    override fun onBindViewHolder(holder: AboutUsHolder, position: Int) {
        //Nombre del alumno
        holder.name.text = list[position].name
        //Imagenes de los colaboradores
        db.collection(FirestoreCollections.USERS).document(list[position].email).get().addOnCompleteListener {
            if(it.isSuccessful){
                Glide.with(activity).load(it.result.getString("profileImage")).into(holder.image)
            }
        }.addOnFailureListener {
            holder.image.setImageResource(
                activity.resources.getIdentifier(list[position].image, "drawable", activity.packageName)
            )
        }
    }

    /**
     * Devuelve el número de objetos que contiene la lista
     */
    override fun getItemCount(): Int {
        return list.size
    }
}