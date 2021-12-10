package com.appverse.hitthecooks.recyclers

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserProfileImageAdapter(private val context: Context,private val imageProfileUrl : ArrayList<String>) : RecyclerView.Adapter<UserProfileImageHolder>() {
    private lateinit var storageRef : StorageReference
    private val db = Firebase.firestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileImageHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_image_profile,parent,false)
       return UserProfileImageHolder(view)
    }

    override fun onBindViewHolder(holder: UserProfileImageHolder, position: Int) {
        var user : User? = null;
        db.collection(FirestoreCollections.USERS).document(imageProfileUrl[position].toString()).get().addOnCompleteListener {
            user = it.result.toObject(User::class.java)
        }.addOnSuccessListener {
            Glide.with(context).load(user?.profileImage).into(holder.userImageProfile)
        }
    }

    override fun getItemCount(): Int {
        return imageProfileUrl.size
    }

}