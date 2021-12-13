package com.appverse.hitthecooks.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

/**
 * Adapter personalizado para el RecyclerView de usuarios invitados
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.2
 * @param context Contexto de la actividad donde mostrar el Recycler
 * @param imageProfileUrl Lista de imagenes de los usuarios participantes de la listas
 */
class UserProfileImageAdapter(private val context: Context,private val imageProfileUrl : ArrayList<String>) : RecyclerView.Adapter<UserProfileImageHolder>() {

    /** Objeto que contiene la instancia al Storage de Firebase **/
    private lateinit var storageRef : StorageReference
    /** Objeto que contiene la instancia a la base de datos de Firebase **/
    private val db = Firebase.firestore

    /**
     * Infla el layout del recycler de colaboradores de la lista
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileImageHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_image_profile,parent,false)
       return UserProfileImageHolder(view)
    }

    /**
     * Da valor a los diferentes elementos del holder
     */
    override fun onBindViewHolder(holder: UserProfileImageHolder, position: Int) {
        var user : User? = null;
        //Carga la imagen de cada usuario de la lista
        db.collection(FirestoreCollections.USERS).document(imageProfileUrl[position].toString()).get().addOnCompleteListener {
            user = it.result.toObject(User::class.java)
        }.addOnSuccessListener {
            Glide.with(context).load(user?.profileImage).into(holder.userImageProfile)
        }
    }

    /**
     * Devuelve el numero de objetos de la lista de colaboradores
     */
    override fun getItemCount(): Int {
        return imageProfileUrl.size
    }

}